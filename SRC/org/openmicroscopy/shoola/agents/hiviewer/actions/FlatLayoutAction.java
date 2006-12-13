/*
 * org.openmicroscopy.shoola.agents.hiviewer.actions.FlatLayoutAction
 *
 *------------------------------------------------------------------------------
 *
 *  Copyright (C) 2004 Open Microscopy Environment
 *      Massachusetts Institute of Technology,
 *      National Institutes of Health,
 *      University of Dundee
 *
 *
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this library; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *------------------------------------------------------------------------------
 */

package org.openmicroscopy.shoola.agents.hiviewer.actions;



//Java imports
import java.awt.event.ActionEvent;
import javax.swing.Action;


//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.hiviewer.IconManager;
import org.openmicroscopy.shoola.agents.hiviewer.cmd.LayoutCmd;
import org.openmicroscopy.shoola.agents.hiviewer.layout.LayoutFactory;
import org.openmicroscopy.shoola.agents.hiviewer.view.HiViewer;

/** 
 * Uses the <code>FlatLayout</code> algorithm to layout the thumbnails.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author	Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 * 	<a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $ $Date: $)
 * </small>
 * @since OME2.2
 */
public class FlatLayoutAction
    extends HiViewerAction
{

    /** The name of the action. */
    private static final String NAME = "Flat";
    
    /**
     * Allows to modify the layout when the thumbnails are loaded.
     * @see HiViewerAction#onStateChange()
     */
    protected void onStateChange()
    {
        //setEnabled(model.getState() == HiViewer.READY);
        setEnabled(true);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param model Reference to the Model. Mustn't be <code>null</code>.
     */
    public FlatLayoutAction(HiViewer model)
    {
        super(model);
        setEnabled(true);
        putValue(Action.NAME, NAME);
        //String description = 
        //    LayoutFactory.getLayoutDescription(LayoutFactory.FLAT_LAYOUT);
        putValue(Action.SHORT_DESCRIPTION, "");
        IconManager icons = IconManager.getInstance();
        putValue(Action.SMALL_ICON, icons.getIcon(IconManager.FLAT_LAYOUT));
    }
    
    /** 
     * Creates a {@link LayoutCmd} command to execute the action. 
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        LayoutCmd cmd = new LayoutCmd(model, LayoutFactory.FLAT_LAYOUT);
        cmd.execute();
    }
    
}
