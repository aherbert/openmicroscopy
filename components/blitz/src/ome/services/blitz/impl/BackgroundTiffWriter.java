package ome.services.blitz.impl;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import loci.formats.FormatException;
import loci.formats.out.TiffWriter;

/**
 * Simple wrapper object for the BioFormats TiffWriter class that allows the 
 * {@link loci.formats.out.TiffWriter#saveBytes(int, byte[])} method to be called 
 * in a background thread. This allows a producer of image bytes to operate 
 * simultaneously with the consumer (BioFormats):
 * 
 * <pre>
    TiffWriter writer = new TiffWriter()
    
    // TODO: Initialise writer here

    BackgroundTiffWriter BackgroundTiffWriter = new BackgroundTiffWriter(writer);
    Thread thread = new Thread(BackgroundTiffWriter);
    thread.start();
    
    try {
        for ( &lt;get planes loop&gt; ) {
            byte[] plane = new byte[planeSize];
            
            // TODO: fill byte[] array

            BackgroundTiffWriter.saveBytes(plane);
        }
        BackgroundTiffWriter.finalise();
        thread.join();
    }
    catch (Exception e) {
        BackgroundTiffWriter.shutdown();
    } 
    finally {
    	// The original writer is not closed by the BackgroundTiffWriter 
        writer.close();
    }
	</pre>
 * 
 * <p>
 * The class uses a BlockingQueue to store successive byte arrays passed to 
 * the {@link #saveBytes(byte[])} method. These are sequentially dequeued and 
 * passed to the TiffWriter. The size of the blocking queue can be set in the
 * constructor. Consequently the producer of the bytes should not get too far
 * ahead of the consumer TiffWriter class.    
 * 
 * <p>
 * The BackgroundTiffWriter expects the {@link loci.formats.TiffWriter } class
 * to be correctly initialised to accept bytes to the 
 * {@link loci.formats.TiffWriter#saveBytes(int, byte[])} method. 
 * 
 * <p>
 * Note: The TiffWriter is not closed by this class. 
 * 
 * @author Alex Herbert, GDSC
 */
public class BackgroundTiffWriter implements Runnable {

	private final static Log log = LogFactory.getLog(BackgroundTiffWriter.class);

	private TiffWriter writer;
	private BlockingQueue<byte[]> queue;
	private volatile boolean closed = false;
	
	private final Object lock = new Object();
	
	/**
	 * Default constructor
	 * @param writer An opened and initialised TiffWriter
	 */
	public BackgroundTiffWriter(TiffWriter writer) {
		this.writer = writer;
		init(5);
	}
	
	/**
	 * Constructor
	 * @param writer An opened and initialised TiffWriter
	 * @param queueSize The size of the blocking queue
	 */
	public BackgroundTiffWriter(TiffWriter writer, int queueSize) {
		this.writer = writer;
		init(queueSize);
	}
	
	/**
	 * Initialise the blocking queue
	 * @param queueSize
	 */
	private void init(int queueSize) {
		queue = new ArrayBlockingQueue<byte[]>(queueSize);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Thread.currentThread();
		try {
			int no = 0;
			byte[] bytes;
			while (!closed) {
				bytes = queue.take();
				if (bytes == null || bytes.length == 0 || closed)
					break;
				synchronized (lock) {
					writer.saveBytes(no, bytes);
				}
				no++;
			}
		}
		catch (InterruptedException e) {
			log.info(e.toString());
			throw new RuntimeException(e);
		} catch (FormatException e) {
			log.error(e.toString());
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error(e.toString());
			throw new RuntimeException(e);
		} finally {
			closed = true;

			// Free remaining memory
			queue.clear();
			queue = null;
			writer = null;

			notifyAll();
		}
	}

	/**
	 * Adds the bytes to the processing queue. A zero length byte array indicates
	 * that no more input is expected.
	 * @param bytes The bytes
	 * @throws RuntimeException If the writer is closed or the thread is interrupted
	 *                          adding to the queue.
	 */
	public void saveBytes(byte[] bytes) throws RuntimeException {
		if (closed)
			throw new RuntimeException("The writer has been closed");
		try {
			queue.put(bytes);
		} catch (InterruptedException iex) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Unexpected interruption", iex);
		}
	}
	
	/**
	 * Send a signal to the queue to indicate no more input is expected.
	 */
	public void finalise() {
		saveBytes(new byte[0]);
	}

	/**
	 * Finish the current write process and stop further writing. 
	 * Use this method to end the processing in the event of error.
	 */
	public void shutdown() {
		if (closed)
			return;
		
		// Ensure that the current write process is over. This allows the 
		// TiffWriter to be closed after shutting down the BackgroundTiffWriter.
		synchronized (lock) {
			closed = true;
			log.info("Terminating ...");
		}
	}

	/**
	 * @return True if no further writes are possible
	 */
	public boolean isClosed() {
		return closed;
	}
}
