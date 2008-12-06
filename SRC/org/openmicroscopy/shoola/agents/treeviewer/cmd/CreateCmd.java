/*
 * org.openmicroscopy.shoola.agents.treeviewer.cmd.CreateCmd
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */

package org.openmicroscopy.shoola.agents.treeviewer.cmd;


//Java imports

//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.events.fsimporter.LoadFSImporter;
import org.openmicroscopy.shoola.agents.treeviewer.TreeViewerAgent;
import org.openmicroscopy.shoola.agents.treeviewer.browser.Browser;
import org.openmicroscopy.shoola.agents.treeviewer.view.TreeViewer;
import org.openmicroscopy.shoola.env.event.EventBus;

import pojos.DataObject;
import pojos.DatasetData;
import pojos.ImageData;
import pojos.PlateData;
import pojos.ProjectData;
import pojos.ScreenData;
import pojos.TagAnnotationData;

/** 
 *  Displays the editor to create a new <code>DataObject</code>.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @version 2.2
 * <small>
 * (<b>Internal version:</b> $Revision$ $Date$)
 * </small>
 * @since OME2.2
 */
public class CreateCmd
    implements ActionCmd
{
    
    /** Indicates to create a <code>Project</code>. */
    public static final int PROJECT = 0;
    
    /** Indicates to create a <code>Dataset</code>. */
    public static final int DATASET = 1;
    
    /** Indicates to create a <code>Tag</code>. */
    public static final int TAG = 2;
    
    /** Indicates to create a <code>Screen</code>. */
    public static final int SCREEN = 3;
    
    /** Indicates to create a <code>Screen</code>. */
    public static final int PLATE = 4;
    
    /** Indicates to import an <code>Image</code>. */
    public static final int IMAGE = 5;
    
    /** Reference to the model. */
    private TreeViewer  model;
    
    /**
     * The <code>DataObject</code> corresponding to a constant
     * defined by this class.
     */
    private DataObject  userObject;
    
    /** Flag indicating if the node to create has a parent. */
    private boolean		withParent;
    
    /**
     * Checks that the specified type is currently supported
     * and returns the corresponding <code>DataObject</code>.
     * 
     * @param type The type to check.
     * @return See above.
     */
    private DataObject checkNodeType(int type)
    {
        switch (type) {
            case PROJECT: return new ProjectData();
            case DATASET: return new DatasetData(); 
            case SCREEN: return new ScreenData(); 
            case TAG: return new TagAnnotationData("foo");
            case PLATE: return new PlateData();
            case IMAGE: return new ImageData();
            default:
                throw new IllegalArgumentException("Type not supported");
        }
    }
    
    /**
     * Creates a new instance.
     * 
     * @param model     Reference to the model. Mustn't be <code>null</code>.
     * @param type      One of the constants defined by this class.
     */
    public CreateCmd(TreeViewer model, int type)
    {
        if (model == null) throw new IllegalArgumentException("No model.");
        userObject = checkNodeType(type);
        this.model = model;
        withParent = true;
    }
    
    /** 
     * Sets to <code>true</code> if the node will have a parent,
     * <code>false</code> otherwise.
     * 
     * @param withParent The value to set.
     */
    public void setWithParent(boolean withParent)
    { 
    	this.withParent = withParent;
    }
    
    /** Implemented as specified by {@link ActionCmd}. */
    public void execute()
    {
        Browser browser = model.getSelectedBrowser();
        if (browser == null) return;
        if (userObject == null) return; //shouldn't happen.
        //model.showProperties(userObject, TreeViewer.CREATE_EDITOR);
        if (userObject instanceof ImageData) {
        	Object object = browser.getLastSelectedDisplay().getUserObject();
        	if (object instanceof DatasetData) {
        		EventBus bus = TreeViewerAgent.getRegistry().getEventBus();
            	bus.post(new LoadFSImporter((DatasetData) object));
        	}
        	
        } else
        	model.createDataObject(userObject, withParent);
    }
    
}
