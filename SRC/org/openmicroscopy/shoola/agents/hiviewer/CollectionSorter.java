/*
 * org.openmicroscopy.shoola.agents.hiviewer.CollectionSorter
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

package org.openmicroscopy.shoola.agents.hiviewer;


//Java imports
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.hiviewer.browser.ImageDisplay;

import pojos.AnnotationData;
import pojos.DataObject;

/** 
 * Helper methods to sort collections.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author  <br>Andrea Falconi &nbsp;&nbsp;&nbsp;&nbsp;
 *              <a href="mailto:a.falconi@dundee.ac.uk">
 *                  a.falconi@dundee.ac.uk</a>
 * @version 2.2
 * <small>
 * (<b>Internal version:</b> $Revision$ $Date$)
 * </small>
 * @since OME2.2
 */
public class CollectionSorter
{

    /**
     * Compares two {@link Date}s.
     * 
     * @param d1 The first object.
     * @param d2 The second object.
     * @return See below.
     */
    private static int compareDates(Date d1, Date d2)
    {
        long n1 = d1.getTime();
        long n2 = d2.getTime();
        int v = 0;
        if (n1 < n2) v = -1;
        else if (n1 > n2) v = 1;
        return v;
    }
    
    /**
     * Compares two {@link String}s.
     * 
     * @param s1 The first object.
     * @param s2 The second object.
     * @return See below.
     */
    private static int compareStrings(String s1, String s2)
    {
        int v = 0;
        int result = s1.compareTo(s2);
        if (result < 0) v = -1;
        else if (result > 0) v = 1;
        return v;
    }
    
    /**
     * Compares two {@link Object}s.
     * 
     * @param o1 The first object.
     * @param o2 The second object.
     * @return See below.
     */
    private static int compareObjects(Object o1, Object o2)
    {
        String s1 = o1.toString();
        String s2 = o2.toString();
        int result = s1.compareTo(s2);
        int v = 0;
        if (result < 0) v = -1;
        else if (result > 0) v = 1;
        return v;
    }
    
    /**
     * Compares two {@link Boolean}s.
     * 
     * @param bool1 The first object.
     * @param bool2 The second object.
     * @return See below.
     */
    private static int compareBooleans(Boolean bool1, Boolean bool2)
    {
        boolean b1 = bool1.booleanValue();
        boolean b2 = bool2.booleanValue();
        int v = 0;
        if (b1 == b2) v = 0;
        else if (b1) v =  -1; //1
        else v = 1;//-1
        return v;
    }
    
    /**
     * Compares two {@link Number}s.
     * 
     * @param n1 The first object.
     * @param n2 The second object.
     * @return See below.
     */
    private static int compareNumbers(Number n1, Number n2)
    {
        double d1 = n1.doubleValue();
        double d2 = n2.doubleValue();
        int v = 0;
        if (d1 < d2) v = -1;
        else if (d1 > d2)   v = 1;
        return v;
    }
    
    private static int compareAnnotationData(AnnotationData a1,
                                            AnnotationData a2)
    {
        Timestamp t1 = a1.getLastModified();
        if (t1 == null) {
            t1 = new Timestamp((new java.util.Date()).getTime());
        }
        Timestamp t2 = a2.getLastModified();
        if (t2 == null) {
            t2 = new Timestamp((new java.util.Date()).getTime());
        }
        int v = 0;
        if (t1.after(t2)) v = -1;
        else if (t1.after(t2)) v = 1;
        return v;
    }
    
    /**
     * Compares two {@link ImageDisplay}s.
     * 
     * @param id1 The first object.
     * @param id2 The second object.
     * @return See below
     */
    private static int compareImageDisplay(ImageDisplay id1, ImageDisplay id2)
    {
        Object o1 = id1.getHierarchyObject();
        Object o2 = id2.getHierarchyObject();
        if (o1 == null && o2 == null) return 0; 
        else if (o1 == null) return -1; 
        else if (o2 == null) return 1; 
        return compareObjects(o1, o2);
    }
    
    // This is a home-grown implementation which we have not had time
    // to research - it may perform poorly in some circumstances. It
    // requires twice the space of an in-place algorithm and makes
    // NlogN assigments shuttling the values between the two
    // arrays. The number of compares appears to vary between N-1 and
    // NlogN depending on the initial order but the main reason for
    // using it here is that, unlike qsort, it is stable.
    private static void shuttlesort(Object[] from, Object[] to, int low,
                                    int high, boolean ascending)
    {
        if (high-low < 2) return;
        int middle = (low+high)/2;
        shuttlesort(to, from, low, middle, ascending);
        shuttlesort(to, from, middle, high, ascending);

        int p = low, q = middle;

        /* This is an optional short-cut; at each recursive call,
        check to see if the elements in this subset are already
        ordered.  If so, no further comparisons are needed; the
        sub-array can just be copied.  The array must be copied rather
        than assigned otherwise sister calls in the recursion might
        get out of sinc.  When the number of elements is three they
        are partitioned so that the first set, [low, mid), has one
        element and and the second, [mid, high), has two. We skip the
        optimisation when the number of elements is three or less as
        the first compare in the normal merge will produce the same
        sequence of steps. This optimisation seems to be worthwhile
        for partially ordered lists but some analysis is needed to
        find out how the performance drops to Nlog(N) as the initial
        order diminishes - it may drop very quickly.  */

        if (high-low >= 4 && compare(from[middle-1], from[middle], 
            ascending) <= 0) {
            for (int i = low; i < high; i++)
                to[i] = from[i];
            return;
        }

        for (int i = low; i < high; i++) {
            if (q >= high || (p < middle && compare(from[p], from[q], 
                ascending) <= 0)) to[i] = from[p++];
            else to[i] = from[q++]; 
        }
    }
    
    /**
     * Compares two {@link Object}s.
     * 
     * @param o1 The first object.
     * @param o2 The second object.
     * @param ascending Pass <code>true</code> to order elements in ascending
     * order, Pass <code>false</code> otherwise.
     * 
     * @return See below.
     */
    private static int compare(Object o1, Object o2, boolean ascending)
    {
        // If both values are null, return 0.
        if (o1 == null && o2 == null) return 0; 
        else if (o1 == null) return -1; 
        else if (o2 == null) return 1; 
        int result = 0;

        if (o1 instanceof Number || o1 instanceof Integer || 
                o1 instanceof Double || o1 instanceof Float)
            result = compareNumbers((Number) o1, (Number) o2);
        else if (o1 instanceof Date) 
            result = compareDates((Date) o1, (Date) o2);
        else if (o1 instanceof String)
            result = compareStrings((String) o1, (String) o2);
        else if (o1 instanceof Boolean)
            result = compareBooleans((Boolean) o1, (Boolean) o2);   
        else if (o1 instanceof ImageDisplay) 
            result = compareImageDisplay((ImageDisplay) o1, (ImageDisplay) o2);
        else if (o1 instanceof AnnotationData) 
            result = compareAnnotationData((AnnotationData) o1,
                        (AnnotationData) o2);
        else result = compareObjects(o1, o2);
            
        if (result != 0) return ascending ? result : -result;
        return result;
    }
    
    /**
     * Sorts the elements of the specified set in ascending order.
     * The elements are DataObject elements.
     * 
     * @param set The set to sort.
     * @return A list of ordered elements.
     */
    public static List sortDataObject(Set set)
    {
        return sortDataObject(set, true);
    }
    
    /**
     * Sorts the elements of the specified set in the specified order.
     * The elements are DataObject elements.
     * 
     * @param set The set to sort.
     * @param ascending Pass <code>true</code> to sort the elements in the 
     * ascending order.
     * @return A list of ordered elements.
     */
    public static List sortDataObject(Set set, boolean ascending)
    {
        Iterator i = set.iterator();
        DataObject[]  array = new DataObject[set.size()];
        DataObject[]  clone = new DataObject[set.size()];
        int index = 0;
        DataObject da;
        while (i.hasNext()) {
            da = (DataObject) i.next();
            array[index] = da;
            clone[index] = da;
            index++;
        }
        shuttlesort(clone, array, 0, array.length, ascending);
        List list = new ArrayList();
        for (int j = 0; j < array.length; j++) 
            list.add(array[j]);
        return list;
    }
    
    /**
     * Sorts the elements of the specified list in ascending order.
     * The elements are DataObject elements.
     * 
     * @param list The list to sort.
     * @return A list of ordered elements.
     */
    public static List sortDataObject(List list)
    {
        return sortDataObject(list, true);
    }
    
    /**
     * Sorts the elements of the specified list in the specified order.
     * The elements are DataObject elements.
     * 
     * @param list The list to sort.
     * @param ascending Pass <code>true</code> to sort the elements in the 
     * ascending order.
     * @return A list of ordered elements.
     */
    public static List sortDataObject(List list, boolean ascending)
    {
        Iterator i = list.iterator();
        DataObject[]  array = new DataObject[list.size()];
        DataObject[]  clone = new DataObject[list.size()];
        int index = 0;
        DataObject da;
        while (i.hasNext()) {
            da = (DataObject) i.next();
            array[index] = da;
            clone[index] = da;
            index++;
        }
        shuttlesort(clone, array, 0, array.length, ascending);
        List newList = new ArrayList();
        for (int j = 0; j < array.length; j++) 
            newList.add(array[j]);
        return newList;
    }
    
    /**
     * Sorts the elements of the specified list in ascending order.
     * The elements are {@link ImageDisplay} elements.
     * 
     * @param set The set to sort.
     * @return A list of ordered elements.
     */
    public static List sortImageDisplay(Set set)
    {
        return sortImageDisplay(set, true);
    }
    
    /**
     * Sorts the elements of the specified set in the specified order.
     * The elements are {@link ImageDisplay} elements.
     * 
     * @param set The set to sort.
     * @param ascending Pass <code>true</code> to sort the elements in the 
     * ascending order.
     * @return A list of ordered elements.
     */
    public static List sortImageDisplay(Set set, boolean ascending)
    {
        Iterator i = set.iterator();
        ImageDisplay[]  array = new ImageDisplay[set.size()];
        ImageDisplay[]  clone = new ImageDisplay[set.size()];
        int index = 0;
        ImageDisplay ia;
        while (i.hasNext()) {
            ia = (ImageDisplay) i.next();
            array[index] = ia;
            clone[index] = ia;
            index++;
        }
        shuttlesort(clone, array, 0, array.length, ascending);
        List list = new ArrayList();
        for (int j = 0; j < array.length; j++)  
            list.add(array[j]);
        return list;
    }
    
    public static List sortAnnotationDataByDate(Set set)
    {
        return sortAnnotationDataByDate(set, true);
    }
    
    public static List sortAnnotationDataByDate(Set set, boolean ascending)
    {
        if (set == null)
            throw new IllegalArgumentException("The set cannot be null.");
        Iterator i = set.iterator();
        AnnotationData[]  array = new AnnotationData[set.size()];
        AnnotationData[]  clone = new AnnotationData[set.size()];
        int index = 0;
        AnnotationData data;
        while (i.hasNext()) {
            data = (AnnotationData) i.next();
            
            //if (time != null) {
                array[index] = data;
                clone[index] = data;
                index++; 
            //}
        }
        shuttlesort(clone, array, 0, array.length, ascending);
        List list = new ArrayList();
        for (int j = 0; j < array.length; j++)  
            list.add(array[j]);
        return list;
    }
}
