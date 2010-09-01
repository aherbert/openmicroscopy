/*
 * integration.XMLMockFactory 
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006-2010 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */
package integration;

//Java imports

//Third-party libraries

//Application-internal dependencies
import ome.xml.model.Arc;
import ome.xml.model.BinData;
import ome.xml.model.Channel;
import ome.xml.model.Annotation;
import ome.xml.model.BooleanAnnotation;
import ome.xml.model.BinaryFile;
import ome.xml.model.CommentAnnotation;
import ome.xml.model.Dataset;
import ome.xml.model.Detector;
import ome.xml.model.DetectorSettings;
import ome.xml.model.Dichroic;
import ome.xml.model.DoubleAnnotation;
import ome.xml.model.Ellipse;
import ome.xml.model.Filament;
import ome.xml.model.FileAnnotation;
import ome.xml.model.Filter;
import ome.xml.model.FilterSet;
import ome.xml.model.Image;
import ome.xml.model.ImagingEnvironment;
import ome.xml.model.Instrument;
import ome.xml.model.Laser;
import ome.xml.model.LightEmittingDiode;
import ome.xml.model.LightPath;
import ome.xml.model.LightSource;
import ome.xml.model.LightSourceSettings;
import ome.xml.model.Line;
import ome.xml.model.LongAnnotation;
import ome.xml.model.Mask;
import ome.xml.model.Microscope;
import ome.xml.model.Objective;
import ome.xml.model.ObjectiveSettings;
import ome.xml.model.OME;
import ome.xml.model.OTF;
import ome.xml.model.Pixels;
import ome.xml.model.Plate;
import ome.xml.model.PlateAcquisition;
import ome.xml.model.Point;
import ome.xml.model.Polyline;
import ome.xml.model.Project;
import ome.xml.model.Rectangle;
import ome.xml.model.Screen;
import ome.xml.model.Shape;
import ome.xml.model.StageLabel;
import ome.xml.model.StructuredAnnotations;
import ome.xml.model.Union;
import ome.xml.model.TagAnnotation;
import ome.xml.model.TermAnnotation;
import ome.xml.model.TransmittanceRange;
import ome.xml.model.ROI;
import ome.xml.model.Well;
import ome.xml.model.WellSample;
import ome.xml.model.enums.ArcType;
import ome.xml.model.enums.Binning;
import ome.xml.model.enums.Compression;
import ome.xml.model.enums.Correction;
import ome.xml.model.enums.DetectorType;
import ome.xml.model.enums.DimensionOrder;
import ome.xml.model.enums.ExperimentType;
import ome.xml.model.enums.FilamentType;
import ome.xml.model.enums.FilterType;
import ome.xml.model.enums.Medium;
import ome.xml.model.enums.Immersion;
import ome.xml.model.enums.LaserType;
import ome.xml.model.enums.MicroscopeType;
import ome.xml.model.enums.NamingConvention;
import ome.xml.model.enums.PixelType;
import ome.xml.model.primitives.NonNegativeInteger;
import ome.xml.model.primitives.NonNegativeLong;
import ome.xml.model.primitives.PercentFraction;
import ome.xml.model.primitives.PositiveInteger;

/** 
 * Creates XML objects.
 *
 * @author Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * <a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author Chris Allan &nbsp;&nbsp;&nbsp;&nbsp;
 * Chris Allan <callan at blackcat dot ca>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $Date: $)
 * </small>
 * @since 3.0-Beta4
 */
class XMLMockObjects 
{

	/** The default power of a light source. */
	static final Double LIGHTSOURCE_POWER = 200.0;

	/** The default model of a component of a microscope. */
	static final String COMPONENT_MODEL = "Model";
	
	/** The default manufacturer of a component of a microscope. */
	static final String COMPONENT_MANUFACTURER = "Manufacturer";
	
	/** The default serial number of a component of a microscope. */
	static final String COMPONENT_SERIAL_NUMBER = "0123456789";
	
	/** The default lot number of a component of a microscope. */
	static final String COMPONENT_LOT_NUMBER = "9876543210";
	
	/** The default type of a laser. */
	static final LaserType LASER_TYPE = LaserType.DYE;
	
	/** The default type of an arc. */
	static final ArcType ARC_TYPE = ArcType.HGXE;
	
	/** The default type of a filament. */
	static final FilamentType FILAMENT_TYPE = FilamentType.HALOGEN;
	
	/** The default type of a detector. */
	static final DetectorType DETECTOR_TYPE = DetectorType.CCD;
	
	/** The default objective's correction. */
	static final Correction CORRECTION = Correction.UV;
	
	/** The default objective's immersion. */
	static final Immersion IMMERSION = Immersion.OIL;
	
	/** The default objective's immersion. */
	static final FilterType FILTER_TYPE = FilterType.LONGPASS;
	
	/** The default type of a microscope. */
	static final MicroscopeType MICROSCOPE_TYPE = MicroscopeType.UPRIGHT;
	
	/** The default binning value. */
	static final Binning BINNING = Binning.TWOXTWO;
	
	/** The default medium for the objective. */
	static final Medium MEDIUM = Medium.AIR;
	
	/** The default number of pixels along the X-axis. */
	static final Integer SIZE_X = 24;
	
	/** The default number of pixels along the Y-axis. */
	static final Integer SIZE_Y = 24;
	
	/** The default number of z-sections. */
	static final Integer SIZE_Z = 1;
	
	/** The default number of channels. */
	static final Integer SIZE_C = 3;
	
	/** The default number of time-points. */
	static final Integer SIZE_T = 1;
	
	/** The number of bytes per pixels. */
	static final Integer BYTES_PER_PIXEL = 2;
	
	/** The default number of rows for a plate. */
	static final int    ROWS = 16;
	
	/** The default number of columns for a plate. */
	static final int    COLUMNS = 24;
	
	/** The default number of fields for a well. */
	static final int    FIELDS = 3;
	
	/** The light sources to handle. */
	static final String[] LIGHT_SOURCES = {Laser.class.getName(), 
		Arc.class.getName(), Filament.class.getName(), 
		LightEmittingDiode.class.getName()};
	
	/** The shapes to handle. */
	static final String[] SHAPES = {Line.class.getName(), 
		Point.class.getName(), Rectangle.class.getName(), 
		Ellipse.class.getName(), Polyline.class.getName(),
		Mask.class.getName()};
	
	/** The default naming convention for rows. */
	private static final NamingConvention ROW_NAMING_CONVENTION = 
		NamingConvention.LETTER;
	
	/** The default naming convention for columns. */
	private static final NamingConvention COLUMN_NAMING_CONVENTION = 
		NamingConvention.NUMBER;
	
	/** The default dimension order. */
	private static final DimensionOrder DIMENSION_ORDER = DimensionOrder.XYZCT;
	
	/** The default pixels type. */
	private static final PixelType PIXEL_TYPE = PixelType.UINT16;

	/** The number of filters created. */
	private static final int NUMBER_OF_FILTERS = 2;
	
	/** The number of dichroics created. */
	private static final int NUMBER_OF_DICHROICS = 1;
	
	/** Points used to create Polyline and Polygon shape. */
	private static final String POINTS = "0,0 10,10";
	
	/** Root of the file. */
	private OME ome;
	
	/** The instrument used for the metadata. */
	private Instrument instrument;

	/** Creates and populates the instrument. */
	private void populateInstrument()
	{
		if (instrument != null) return;
		instrument = createInstrument(true);
		ome.addInstrument(instrument);
	}
	
	/**
	 * Creates a detector.
	 * 
	 * @param index The index of the detector in the file.
	 * @return See above.
	 */
	private Detector createDetector(int index)
	{
		Detector detector = new Detector();
		detector.setID("Detector:"+index);
		detector.setModel(COMPONENT_MODEL);
		detector.setManufacturer(COMPONENT_MANUFACTURER);
		detector.setSerialNumber(COMPONENT_SERIAL_NUMBER);
		detector.setAmplificationGain(0.0);
    	detector.setGain(1.0);
		return detector;
	}
	
	/**
	 * Creates a filter set.
	 * 
	 * @param index The index of the filter set in the file.
	 * @return See above.
	 */
	private FilterSet createFilterSet(int index)
	{
		FilterSet set = new FilterSet();
		set.setID("FilterSet:"+index);
		set.setModel(COMPONENT_MODEL);
		set.setManufacturer(COMPONENT_MANUFACTURER);
		set.setSerialNumber(COMPONENT_SERIAL_NUMBER);
		return set;
	}
	
	/**
	 * Creates a microscope.
	 * 
	 * @return See above.
	 */
	private Microscope createMicroscope()
	{
		Microscope microscope = new Microscope();
		microscope.setManufacturer(COMPONENT_MANUFACTURER);
		microscope.setModel(COMPONENT_MODEL);
		microscope.setSerialNumber(COMPONENT_SERIAL_NUMBER);
		microscope.setType(MICROSCOPE_TYPE);
		return microscope;
	}
	
	/**
	 * Creates a dichroic.
	 * 
	 * @param index The index of the dichroic in the file.
	 * @return See above.
	 */
	private Dichroic createDichroic(int index)
	{
		Dichroic dichroic = new Dichroic();
		dichroic.setID("Dichroic:"+index);
		dichroic.setModel(COMPONENT_MODEL);
		dichroic.setManufacturer(COMPONENT_MANUFACTURER);
		dichroic.setLotNumber(COMPONENT_LOT_NUMBER);
		return dichroic;
	}
	
	/**
	 * Creates an objective.
	 * 
	 * @param index The index of the objective in the file.
	 * @return See above.
	 */
	private Objective createObjective(int index)
	{
		Objective objective = new Objective();
		objective.setID("Objective:"+index);
		objective.setModel(COMPONENT_MODEL);
		objective.setManufacturer(COMPONENT_MANUFACTURER);
		objective.setSerialNumber(COMPONENT_SERIAL_NUMBER);
		objective.setCalibratedMagnification(1.0);
		objective.setCorrection(CORRECTION);
		objective.setImmersion(IMMERSION);
		objective.setIris(true);
		objective.setLensNA(0.5);
		objective.setNominalMagnification(new PositiveInteger(1));
		objective.setWorkingDistance(1.0);
		return objective;
	}
	
	/**
	 * Creates a filter.
	 * 
	 * @param index The index of the objective in the file.
	 * @param cutIn The cut in value.
     * @param cutOut The cut out value.
	 * @return See above.
	 */
	private Filter createFilter(int index, int cutIn, int cutOut)
	{
		Filter filter = new Filter();
		filter.setID("Filter:"+index);
		filter.setModel(COMPONENT_MODEL);
		filter.setManufacturer(COMPONENT_MANUFACTURER);
		filter.setLotNumber(COMPONENT_LOT_NUMBER);
		filter.setType(FILTER_TYPE);

		TransmittanceRange transmittance = new TransmittanceRange();
		transmittance.setCutIn(new PositiveInteger(cutIn));
		transmittance.setCutOut(new PositiveInteger(cutOut));
		filter.setTransmittanceRange(transmittance);
		return filter;
	}
	
	/**
	 * Creates a light source of the specified type.
	 * 
	 * @param type The type of light source to create.
	 * @param index The index of the light source in the file.
	 * @return See above.
	 */
	private LightSource createLightSource(String type, int index)
	{
		if (Laser.class.getName().equals(type)) {
			Laser laser = new Laser();
			laser.setID("LightSource:"+index);
			laser.setModel(COMPONENT_MODEL);
			laser.setManufacturer(COMPONENT_MANUFACTURER);
			laser.setPower(LIGHTSOURCE_POWER);
			laser.setType(LASER_TYPE);
			return laser;
		} else if (Arc.class.getName().equals(type)) {
			Arc arc = new Arc();
			arc.setID("LightSource:"+index);
			arc.setManufacturer(COMPONENT_MANUFACTURER);
			arc.setModel(COMPONENT_MODEL);
			arc.setPower(LIGHTSOURCE_POWER);
			arc.setType(ARC_TYPE);
			return arc;
		} else if (Filament.class.getName().equals(type)) {
			Filament filament = new Filament();
			filament.setID("LightSource:"+index);
			filament.setManufacturer(COMPONENT_MANUFACTURER);
			filament.setModel(COMPONENT_MODEL);
			filament.setPower(LIGHTSOURCE_POWER);
			filament.setType(FILAMENT_TYPE);
			return filament;
		} else if (LightEmittingDiode.class.getName().equals(type)) {
			LightEmittingDiode light = new LightEmittingDiode();
			light.setID("LightSource:"+index);
			light.setManufacturer(COMPONENT_MANUFACTURER);
			light.setModel(COMPONENT_MODEL);
			light.setPower(LIGHTSOURCE_POWER);
			return light;
		}
		return null;
	}
	
	/**
	 * Creates a new object.
	 * 
	 * @param sizeX The number of pixels along the X-axis.
	 * @param sizeY The number of pixels along the Y-axis.
	 * @param bpp   The number of bytes per pixels.
	 * @return See above.
	 */
	private BinData createBinData(int sizeX, int sizeY, int bpp)
	{
		BinData data = new BinData();
		data.setBigEndian(false);
		data.setCompression(Compression.NONE);
		data.setLength(new NonNegativeLong((long) (sizeX*sizeY*bpp)));
		return data;
	}
	
	/**
	 * Creates a light path.
	 * 
	 * @return See above.
	 */
	private LightPath createLightPath()
	{
		LightPath lp = new LightPath();
		if (NUMBER_OF_DICHROICS > 0) 
			lp.linkDichroic(instrument.getDichroic(0));
		if (NUMBER_OF_FILTERS == 1)
			lp.linkEmissionFilter(instrument.getFilter(0));
		else if (NUMBER_OF_FILTERS >= 2) {
			lp.linkEmissionFilter(instrument.getFilter(0));
			lp.linkExcitationFilter(instrument.getFilter(1));
		}
		
		return lp;
	}
	
	/**
	 * Creates a imaging environment.
	 * 
	 * @return See above.
	 */
	private ImagingEnvironment createImageEnvironment()
	{
		ImagingEnvironment env = new ImagingEnvironment();
		env.setAirPressure(1.0);
		env.setCO2Percent(new PercentFraction(1.0f));
		env.setHumidity(new PercentFraction(1.0f));
		env.setTemperature(1.0);
		return env;
	}
	
	/**
	 * Creates a imaging environment.
	 * 
	 * @param index The index of the environment in the file.
	 * @return See above.
	 */
	private StageLabel createStageLabel()
	{
		StageLabel label = new StageLabel();
		label.setName("StageLabel");
		label.setX(1.0);
		label.setY(1.0);
		label.setZ(1.0);
		return label;
	}
	
	/**
	 * Creates a light source settings.
	 * 
	 * @param ref Reference to the light source.
	 * @return See above.
	 */
	private LightSourceSettings createLightSourceSettings(int ref)
	{
		LightSourceSettings settings = new LightSourceSettings();
		settings.setID("LigthSource:"+ref);
		settings.setAttenuation(new PercentFraction(1.0f));
		settings.setWavelength(new PositiveInteger(200));
		return settings;
	}
	
	/**
	 * Creates a detector settings.
	 * 
	 * @param ref Reference to the detector.
	 * @return See above.
	 */
	private DetectorSettings createDetectorSettings(int ref)
	{
		DetectorSettings settings = new DetectorSettings();
		settings.setID("Detector:"+ref);
		settings.setBinning(BINNING);
		settings.setGain(1.0);
		settings.setOffset(1.0);
		settings.setReadOutRate(1.0);
    	settings.setVoltage(1.0);
		return settings;
	}
	
	/**
	 * Creates an objective settings.
	 * 
	 * @param ref Reference to the objective.
	 * @return See above.
	 */
	private ObjectiveSettings createObjectiveSettings(int ref)
	{
		ObjectiveSettings settings = new ObjectiveSettings();
		settings.setID("Objective:"+ref);
		settings.setMedium(MEDIUM);
		settings.setCorrectionCollar(1.0);
		settings.setRefractiveIndex(1.0);
		return settings;
	}
	
	/**
	 * Creates an OTF.
	 * 
	 * @param index The index of the OTF.
	 * @param set   The related filter set.
	 * @param settings The related settings.
	 * @return See above.
	 */
	private OTF createOTF(int index, FilterSet set, ObjectiveSettings settings)
	{
		OTF otf = new OTF();
		otf.setID("OTF:"+index);
		otf.setOpticalAxisAveraged(true);
		otf.setObjectiveSettings(settings);
		otf.setSizeX(new PositiveInteger(SIZE_X));
		otf.setSizeY(new PositiveInteger(SIZE_Y));
		otf.setType(PIXEL_TYPE);
		otf.linkFilterSet(set);
		otf.setBinaryFile(createBinaryFile());
		return otf;
	}
	
	/**
	 * Creates a binary file.
	 * 
	 * @return See above.
	 */
	private BinaryFile createBinaryFile()
	{
		BinaryFile bf = new BinaryFile();
		bf.setBinData(createBinData(SIZE_X, SIZE_Y, BYTES_PER_PIXEL));
		return bf;
	}
	
	/**
	 * Creates the specified type of shape.
	 * 
	 * @param index The index of the shape in the file.
	 * @param type The type of shape to create.
	 * @param z    The selected z-section.
	 * @param c    The selected channel.
	 * @param t    The selected time-point.
	 * @return See above.
	 */
	private Shape createShape(int index, String type, int z, int c, int t)
	{
		Shape shape = null;
		if (Line.class.getName().equals(type)) {
			Line line = new Line();
			line.setX1(0.0);
			line.setY1(0.0);
			line.setX2(1.0);
			line.setY2(1.0);
			shape = line;
		} else if (Rectangle.class.getName().equals(type)) {
			Rectangle r = new Rectangle();
			r.setX(0.0);
			r.setY(0.0);
			r.setWidth(10.0);
			r.setHeight(10.0);
			shape = r;
		} else if (Ellipse.class.getName().equals(type)) {
			Ellipse e = new Ellipse();
			e.setRadiusX(1.0);
			e.setRadiusY(1.0);
			e.setY(2.0);
			e.setX(2.0);
			shape = e;
		} else if (Point.class.getName().equals(type)) {
			Point p = new Point();
			p.setY(2.0);
			p.setX(2.0);
			shape = p;
		} else if (Polyline.class.getName().equals(type)) {
			Polyline pl = new Polyline();
			pl.setClosed(false);
			pl.setPoints(POINTS);
			shape = pl;
		} else if (Mask.class.getName().equals(type)) {
			Mask m = new Mask();
			m.setX(0.0);
			m.setY(0.0);
			m.setWidth(new Double(SIZE_X));
			m.setHeight(new Double(SIZE_Y));
			shape = m;
		}
		if (shape != null) {
			shape.setID("Shape:"+index);
			shape.setTheC(new NonNegativeInteger(c));
			shape.setTheZ(new NonNegativeInteger(z));
			shape.setTheT(new NonNegativeInteger(t));
			shape.setTransform("transform"+index);
			shape.setFill(10);
			shape.setStroke(255);
		}
		return shape;
	}
	
	/** 
	 * Creates an ROI.
	 * 
	 * @param index The index of the ROI in the file.
	 * @param z The selected z-section.
	 * @param c The selected channel.
	 * @param t The selected time-point.
	 */
	private ROI createROI(int index, int z, int c, int t)
	{
		ROI roi = new ROI();
		roi.setID("ROI:"+index);
		int n = SHAPES.length;
		Shape shape;
		int j = index;
		if (index > 0) j += n;
		Union union = new Union();
		for (int i = 0; i < n; i++) {
			j += i;
			union.addShape(createShape(j, SHAPES[i], z, c, t));
		}
		roi.setUnion(union);
		return roi;
	}
	
	/** Creates a new instance. */
	XMLMockObjects()
	{
		ome = new OME();
	}

	/** 
	 * Returns the root of the XML file.
	 * 
	 * @return See above.
	 */
	OME getRoot() { return ome; }
	
	/**
	 * Creates a project.
	 * 
	 * @param index The index of the project.
	 * @return See above.
	 */
	Project createProject(int index)
	{
		Project project = new Project();
		project.setID("Project:"+index);
		project.setName("Project Name "+index);
		project.setDescription("Project Description "+index);
		return project;
	}
	
	/**
	 * Creates a dataset.
	 * 
	 * @param index The index of the dataset.
	 * @return See above.
	 */
	Dataset createDataset(int index)
	{
		Dataset dataset = new Dataset();
		dataset.setID("Dataset:"+index);
		dataset.setName("Dataset Name "+index);
		dataset.setDescription("Dataset Description "+index);
		return dataset;
	}
	
	/**
	 * Creates a screen.
	 * 
	 * @param index The index of the screen.
	 * @return See above.
	 */
	Screen createScreen(int index)
	{
		Screen screen = new Screen();
		screen.setID("Screen:"+index);
		screen.setName("Screen Name "+index);
		screen.setDescription("Screen Description "+index);
		return screen;
	}
	
	/**
	 * Creates a basic plate.
	 * 
	 * @param index The index of the plate.
	 * @return See above.
	 */
	Plate createBasicPlate(int index)
	{
		Plate plate = new Plate();
		plate.setID("Plate:"+index);
		plate.setName("Plate Name "+index);
		plate.setDescription("Plate Description "+index);
		return plate;
	}
	
	/**
	 * Creates a default plate
	 * 
	 * @param index The index of the plate.
	 * @param plateAcquisition  Pass <code>true</code> to create a 
	 * plate acquisition, <code>false</code> otherwise.
	 * @return See above.
	 */
	Plate createPlate(int index, boolean plateAcquisition)
	{
		return createPlate(index, ROWS, COLUMNS, FIELDS, plateAcquisition);
	}
	
	/**
	 * Creates a populated plate with images.
	 * 
	 * @param index The index of the plate.
	 * @param rows  The number of rows.
	 * @param columns The number of columns.
	 * @param fields  The number of fields.
	 * @param plateAcquisition Pass <code>true</code> to create a 
	 * plate acquisition, <code>false</code> otherwise.
	 * @return See above.
	 */
	Plate createPlate(int index, int rows, int columns, int fields, boolean 
			plateAcquisition)
	{
		Plate plate = new Plate();
		plate.setID("Plate:"+index);
		plate.setName("Plate Name "+index);
		plate.setDescription("Plate Description "+index);
		plate.setRows(new PositiveInteger(rows));
		plate.setColumns(new PositiveInteger(columns));
		plate.setRowNamingConvention(ROW_NAMING_CONVENTION);
		plate.setColumnNamingConvention(COLUMN_NAMING_CONVENTION);
		
		PlateAcquisition pa = null;
		if (plateAcquisition) {
			pa = new PlateAcquisition();
			pa.setID("PlateAcquistion:"+index);
			pa.setName("PlateAcquistion Name "+index);
			pa.setDescription("PlateAcquistion Description "+index);
		}
		//now populate the plate
		Well well;
		WellSample sample;
		Image image;
		int i = 0;
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				well = new Well();
				well.setID(String.format("Well:%d_%d", row, column));
				well.setRow(new NonNegativeInteger(row));
				well.setColumn(new NonNegativeInteger(column));
				for (int field = 0; field < fields; field++) {
					sample = new WellSample();
					sample.setID(String.format("WellSample:%d_%d", row, column));
					sample.setIndex(new NonNegativeInteger(i));
					//create an image. and register it
					image = createImage(i, true);
					ome.addImage(image);
					sample.linkImage(image);
					if (pa != null) {
						pa.setLinkedWellSample(i, sample);
					}
					well.addWellSample(sample);
					i++;
				}
				plate.addWell(well);
			}
		}
		return plate;
	}
	
	/** 
	 * Creates a new image.
	 * 
	 * @param index The identifier of the image.
	 * @param metadata Pass <code>true</code> to create channel with acquisition
	 * 	               metadata, <code>false</code> otherwise.
	 * @return See above.
	 */
	Image createImage(int index, boolean metadata)
	{
		if (metadata && instrument == null)
			populateInstrument();
		
		Image image = new Image();
		image.setID("Image:"+index);
		image.setName("Image Name "+index);
		image.setDescription("Image Description "+index);
		OTF otf = null;
		if (metadata) {
			image.setImagingEnvironment(createImageEnvironment());
			image.setStageLabel(createStageLabel());
			//instrument has one objective.
			ObjectiveSettings settings = createObjectiveSettings(0);
			otf = createOTF(0, instrument.getFilterSet(0), settings);
			instrument.addOTF(otf);
			image.setObjectiveSettings(settings); 
		}
		Pixels pixels = new Pixels();
		pixels.setID("Pixels:"+index);
		pixels.setSizeX(new PositiveInteger(SIZE_X));
		pixels.setSizeY(new PositiveInteger(SIZE_Y));
		pixels.setSizeZ(new PositiveInteger(SIZE_Z));
		pixels.setSizeC(new PositiveInteger(SIZE_C));
		pixels.setSizeT(new PositiveInteger(SIZE_T));
		pixels.setDimensionOrder(DIMENSION_ORDER);
		pixels.setType(PIXEL_TYPE);
		BinData data;
		for (int i = 0; i < SIZE_Z*SIZE_C*SIZE_T; i++) {
			data = createBinData(SIZE_X, SIZE_Y, BYTES_PER_PIXEL);
			pixels.addBinData(data);
		}
		Channel channel;
		int j = 0;
		int n = LIGHT_SOURCES.length;
		LightPath lp;
		for (int i = 0; i < SIZE_C; i++) {
			channel = channel = new Channel();
			channel.setID("Channel:"+i);
			if (metadata) {
				if (j == n) j = 0;
				channel.setLightSourceSettings(createLightSourceSettings(j));
				channel.setLightPath(createLightPath());
				//link the channel to the OTF
				if (otf != null)
					otf.linkChannel(channel);
				j++;
			}
			pixels.addChannel(channel);
		}
		
		image.setPixels(pixels);
		return image;
	}
	
	/** 
	 * Creates a new image.
	 * 
	 * @param index The identifier of the image.
	 * @return See above.
	 */
	Image createImage(int index)
	{
		return createImage(index, false);
	}
	
	/**
	 * Creates an instrument with filters, light sources etc.
	 * 
	 * @param populate Pass <code>true</code> to populate the instrument, 
	 *                 <code>false</code> otherwise.
	 * @return See above.
	 */
	Instrument createInstrument(boolean populate)
	{
		int index = 0;
		Instrument instrument = new Instrument();
		instrument.setID("Instrument:"+index);
		instrument.setMicroscope(createMicroscope());
		if (populate) {
			instrument.addDetector(createDetector(index));
			instrument.addObjective(createObjective(index));
			instrument.addFilterSet(createFilterSet(index));
			for (int i = 0; i < NUMBER_OF_FILTERS; i++) {
				instrument.addFilter(createFilter(i, 200, 300));
			}
			for (int i = 0; i < NUMBER_OF_DICHROICS; i++) {
				instrument.addDichroic(createDichroic(i));
			}
			for (int i = 0; i < LIGHT_SOURCES.length; i++) {
				instrument.addLightSource(createLightSource(LIGHT_SOURCES[i], 
						i));
			}
		}
		return instrument;
	}

	//annotations
	/**
	 * Create a comment annotation for the specified object.
	 * 
	 * @param type The type of annotation to create.
	 * @param rootType The type of object to attach the annotation to.
	 * @param index The index of the annotation.
	 * @return See above.
	 */
	Annotation createAnnotation(String type, String rootType, int index)
	{
		if (Image.class.getName().equals(rootType)) {
			if (CommentAnnotation.class.getName().equals(type)) {
				CommentAnnotation c = new CommentAnnotation();
				c.setID("ImageCommentAnnotation:" + index);
				c.setValue("Image:"+index+" CommentAnnotation.");
				return c;
			} else if (BooleanAnnotation.class.getName().equals(type)) {
				BooleanAnnotation b = new BooleanAnnotation();
				b.setID("ImageBooleanAnnotation:" + index);
				b.setValue(true);
				return b;
			} else if (LongAnnotation.class.getName().equals(type)) {
				LongAnnotation l = new LongAnnotation();
				l.setID("ImageLongAnnotation:" + index);
				l.setValue(1L);
				return l;
			} else if (TagAnnotation.class.getName().equals(type)) {
				TagAnnotation tag = new TagAnnotation();
				tag.setID("ImageTagAnnotation:" + index);
				tag.setValue("Image:"+index+" TagAnnotation.");
				return tag;
			} else if (TermAnnotation.class.getName().equals(type)) {
				TermAnnotation term = new TermAnnotation();
				term.setID("ImageTermAnnotation:" + index);
				term.setValue("Image:"+index+" TermAnnotation.");
				return term;
			} else if (FileAnnotation.class.getName().equals(type)) {
				FileAnnotation f = new FileAnnotation();
				f.setID("ImageFileAnnotation:" + index);
				f.setBinaryFile(createBinaryFile());
				return f;
			}
		} else if (Plate.class.getName().equals(rootType)) {
			if (CommentAnnotation.class.getName().equals(type)) {
				CommentAnnotation c = new CommentAnnotation();
				c.setID("PlateCommentAnnotation:" + index);
				c.setValue("Plate:"+index+" CommentAnnotation.");
				return c;
			} else if (BooleanAnnotation.class.getName().equals(type)) {
				BooleanAnnotation b = new BooleanAnnotation();
				b.setID("PlateBooleanAnnotation:" + index);
				b.setValue(true);
				return b;
			} else if (LongAnnotation.class.getName().equals(type)) {
				LongAnnotation l = new LongAnnotation();
				l.setID("PlateLongAnnotation:" + index);
				l.setValue(1L);
				return l;
			} else if (TagAnnotation.class.getName().equals(type)) {
				TagAnnotation tag = new TagAnnotation();
				tag.setID("PlateTagAnnotation:" + index);
				tag.setValue("Plate:"+index+" TagAnnotation.");
				return tag;
			} else if (TermAnnotation.class.getName().equals(type)) {
				TermAnnotation term = new TermAnnotation();
				term.setID("PlateTermAnnotation:" + index);
				term.setValue("Plate:"+index+" TermAnnotation.");
				return term;
			} else if (FileAnnotation.class.getName().equals(type)) {
				FileAnnotation f = new FileAnnotation();
				f.setID("PlateFileAnnotation:" + index);
				f.setBinaryFile(createBinaryFile());
				return f;
			}
		} else if (Well.class.getName().equals(rootType)) {
			if (CommentAnnotation.class.getName().equals(type)) {
				CommentAnnotation c = new CommentAnnotation();
				c.setID("WellCommentAnnotation:" + index);
				c.setValue("Well:"+index+" CommentAnnotation.");
				return c;
			} else if (BooleanAnnotation.class.getName().equals(type)) {
				BooleanAnnotation b = new BooleanAnnotation();
				b.setID("WellBooleanAnnotation:" + index);
				b.setValue(true);
				return b;
			} else if (LongAnnotation.class.getName().equals(type)) {
				LongAnnotation l = new LongAnnotation();
				l.setID("WellLongAnnotation:" + index);
				l.setValue(1L);
				return l;
			} else if (TagAnnotation.class.getName().equals(type)) {
				TagAnnotation tag = new TagAnnotation();
				tag.setID("WellTagAnnotation:" + index);
				tag.setValue("Well:"+index+" TagAnnotation.");
				return tag;
			} else if (TermAnnotation.class.getName().equals(type)) {
				TermAnnotation term = new TermAnnotation();
				term.setID("WellTermAnnotation:" + index);
				term.setValue("Well:"+index+" TermAnnotation.");
				return term;
			} else if (FileAnnotation.class.getName().equals(type)) {
				FileAnnotation f = new FileAnnotation();
				f.setID("WellFileAnnotation:" + index);
				f.setBinaryFile(createBinaryFile());
				return f;
			}
		} else if (WellSample.class.getName().equals(rootType)) {
			if (CommentAnnotation.class.getName().equals(type)) {
				CommentAnnotation c = new CommentAnnotation();
				c.setID("WellSampleCommentAnnotation:" + index);
				c.setValue("WellSample:"+index+" CommentAnnotation.");
				return c;
			} else if (BooleanAnnotation.class.getName().equals(type)) {
				BooleanAnnotation b = new BooleanAnnotation();
				b.setID("WellSampleBooleanAnnotation:" + index);
				b.setValue(true);
				return b;
			} else if (LongAnnotation.class.getName().equals(type)) {
				LongAnnotation l = new LongAnnotation();
				l.setID("WellSampleLongAnnotation:" + index);
				l.setValue(1L);
				return l;
			} else if (TagAnnotation.class.getName().equals(type)) {
				TagAnnotation tag = new TagAnnotation();
				tag.setID("WellSampleTagAnnotation:" + index);
				tag.setValue("WellSample:"+index+" TagAnnotation.");
				return tag;
			} else if (TermAnnotation.class.getName().equals(type)) {
				TermAnnotation term = new TermAnnotation();
				term.setID("WellSampleTermAnnotation:" + index);
				term.setValue("WellSample:"+index+" TermAnnotation.");
				return term;
			} else if (FileAnnotation.class.getName().equals(type)) {
				FileAnnotation f = new FileAnnotation();
				f.setID("WellSampleFileAnnotation:" + index);
				f.setBinaryFile(createBinaryFile());
				return f;
			}
		}
		return null;
	}
	
	//Collection of helper methods.
	
	/**
	 * Creates and returns the root element.
	 * 
	 * @return See above.
	 */
	OME createImage()
	{
		ome.addImage(createImage(0));
		return ome;
	}
	
	/**
	 * Creates and annotates an image.
	 * The following types of annotations are added:
	 * TagAnnotation, TermAnnotation, BooleanAnnotation, LongAnnotation,
	 * CommentAnnotation.
	 * 
	 * @return See above.
	 */
	OME createAnnotatedImage()
	{
		StructuredAnnotations annotations = new StructuredAnnotations();
		int index = 0;
		ome.addImage(createImage(index));
		
		String type = Image.class.getName();
		annotations.addCommentAnnotation((CommentAnnotation) createAnnotation(
				CommentAnnotation.class.getName(), type, index));
		annotations.addBooleanAnnotation((BooleanAnnotation) createAnnotation(
				BooleanAnnotation.class.getName(), type, index));
		annotations.addLongAnnotation((LongAnnotation) createAnnotation(
				LongAnnotation.class.getName(), type, index));
		annotations.addTagAnnotation((TagAnnotation) createAnnotation(
				TagAnnotation.class.getName(), type, index));
		annotations.addTermAnnotation((TermAnnotation) createAnnotation(
				TermAnnotation.class.getName(), type, index));
		
		ome.setStructuredAnnotations(annotations);
		return ome;
	}
	
	/**
	 * Creates an image with acquisition data.
	 * 
	 * @return See above.
	 */
	OME createImageWithAcquisitionData()
	{
		populateInstrument();
		ome.addImage(createImage(0, true));
		return ome;
	}
	
	/**
	 * Creates an image with ROI.
	 * 
	 * @return See above.
	 */
	OME createImageWithROI()
	{
		int index = 0;
		Image image = createImage(index, true);
		ROI roi;
		for (int i = 0; i < SIZE_C; i++) {
			roi = createROI(i, 0, 0, i);
			roi.linkImage(image);
			ome.addROI(roi);
		}
		return ome;
	}
	
	/**
	 * Creates a plate with {@link #ROWS} rows, {@link #COLUMNS} columns
	 * and {@link #FIELDS} fields. 
	 * The plate will have images with acquisition data but no plate acquisition
	 * data.
	 * 
	 * @return See above
	 */
	OME createPlate()
	{
		populateInstrument();
		ome.addPlate(createPlate(0, false));
		return ome;
	}
	
	/**
	 * Creates a plate with {@link #ROWS} rows and {@link #COLUMNS}
	 * and {@link #FIELDS}. The plate will have images with acquisition data.
	 * This plate will have one plate acquisition.
	 * 
	 * @return See above
	 */
	OME createPlateWithPlateAcquistion()
	{
		populateInstrument();
		ome.addPlate(createPlate(0, true));
		return ome;
	}
	
}