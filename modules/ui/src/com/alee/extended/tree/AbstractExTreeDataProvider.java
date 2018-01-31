/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.extended.tree;

import com.alee.laf.tree.UniqueNode;
import com.alee.utils.compare.Filter;

import java.util.Comparator;
import java.util.List;

/**
 * Abstract {@link ExTreeDataProvider} that can contain single comparator and filter for child nodes.
 *
 * @param <E> node type
 * @author Mikle Garin
 */

public abstract class AbstractExTreeDataProvider<E extends UniqueNode> implements ExTreeDataProvider<E>
{
    /**
     * {@link Comparator} for all child nodes.
     * It is {@code transient} as it can only be set through code.
     * Override {@link #getChildrenComparator(UniqueNode, List)} method to provide parent-related {@link Comparator}.
     */
    protected transient Comparator<E> comparator = null;

    /**
     * {@link Filter} for all child nodes.
     * It is {@code transient} as it can only be set through code.
     * Override {@link #getChildrenFilter(UniqueNode, List)} method to provide parent-related {@link Filter}.
     */
    protected transient Filter<E> filter = null;

    @Override
    public Comparator<E> getChildrenComparator ( final E parent, final List<E> children )
    {
        return comparator;
    }

    /**
     * Sets children comparator for all nodes.
     *
     * @param comparator children comparator for all nodes
     */
    public void setChildrenComparator ( final Comparator<E> comparator )
    {
        this.comparator = comparator;
    }

    @Override
    public Filter<E> getChildrenFilter ( final E parent, final List<E> children )
    {
        return filter;
    }

    /**
     * Sets children filter for all nodes.
     *
     * @param filter children filter for all nodes
     */
    public void setChildrenFilter ( final Filter<E> filter )
    {
        this.filter = filter;
    }
}