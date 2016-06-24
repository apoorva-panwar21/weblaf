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

package com.alee.extended.label;

import com.alee.managers.hotkey.HotkeyData;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.language.LanguageMethods;
import com.alee.managers.language.LanguageUtils;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.language.updaters.LanguageUpdater;
import com.alee.managers.log.Log;
import com.alee.managers.style.*;
import com.alee.managers.tooltip.ToolTipMethods;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.WebCustomTooltip;
import com.alee.painter.Paintable;
import com.alee.painter.Painter;
import com.alee.painter.decoration.content.StyledText;
import com.alee.painter.decoration.content.TextWrap;
import com.alee.utils.CollectionUtils;
import com.alee.utils.swing.MouseButton;
import com.alee.utils.swing.extensions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Custom label component that quickly renders multi-styled text.
 * Its rendering speed is superior to HTML rendering within simple JLabel and its usage is preferred.
 *
 * @author Mikle Garin
 */

public class WebStyledLabel extends JLabel
        implements Styleable, Paintable, ShapeProvider, MarginSupport, PaddingSupport, EventMethods, ToolTipMethods, LanguageMethods,
        FontMethods<WebStyledLabel>
{
    /**
     * Component properties.
     */
    public static final String STYLE_RANGES_PROPERTY = "styleRanges";
    public static final String WRAP_PROPERTY = "wrap";
    public static final String ROWS_PROPERTY = "rows";
    public static final String MAXIMUM_ROWS_PROPERTY = "maximumRows";
    public static final String MINIMUM_ROWS_PROPERTY = "minimumRows";
    public static final String HORIZONTAL_TEXT_ALIGNMENT_PROPERTY = "horizontalTextAlignment";
    public static final String VERTICAL_TEXT_ALIGNMENT_PROPERTY = "verticalTextAlignment";

    /**
     * StyleRange list.
     */
    protected List<StyleRange> styleRanges;

    /**
     * Text wrapping type.
     */
    protected TextWrap wrap;

    /**
     * Horizontal text alignment.
     */
    protected int horizontalTextAlignment;

    /**
     * Vertical text alignment.
     */
    protected int verticalTextAlignment;

    /**
     * Amount of rows used to wrap label text.
     */
    protected int rows;

    /**
     * Maximum amount of rows.
     */
    protected int maximumRows;

    /**
     * Minimum amount of rows.
     */
    protected int minimumRows;

    /**
     * Constructs empty label.
     */
    public WebStyledLabel ()
    {
        this ( StyleId.styledlabel );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param icon icon
     */
    public WebStyledLabel ( final Icon icon )
    {
        this ( StyleId.styledlabel, icon );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param horizontalAlignment horizontal alignment
     */
    public WebStyledLabel ( final int horizontalAlignment )
    {
        this ( StyleId.styledlabel, horizontalAlignment );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param icon                label icon
     * @param horizontalAlignment horizontal alignment
     */
    public WebStyledLabel ( final Icon icon, final int horizontalAlignment )
    {
        this ( StyleId.styledlabel, icon, horizontalAlignment );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param text text or translation key
     * @param data language data, may not be passed
     */
    public WebStyledLabel ( final String text, final Object... data )
    {
        this ( StyleId.styledlabel, text, data );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param text                text or translation key
     * @param horizontalAlignment horizontal alignment
     * @param data                language data, may not be passed
     */
    public WebStyledLabel ( final String text, final int horizontalAlignment, final Object... data )
    {
        this ( StyleId.styledlabel, text, horizontalAlignment, data );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param text text or translation key
     * @param icon label icon
     * @param data language data, may not be passed
     */
    public WebStyledLabel ( final String text, final Icon icon, final Object... data )
    {
        this ( StyleId.styledlabel, text, icon, data );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param text                text or translation key
     * @param icon                label icon
     * @param horizontalAlignment horizontal alignment
     * @param data                language data, may not be passed
     */
    public WebStyledLabel ( final String text, final Icon icon, final int horizontalAlignment, final Object... data )
    {
        this ( StyleId.styledlabel, text, icon, horizontalAlignment, data );
    }

    /**
     * Constructs empty label.
     *
     * @param id style ID
     */
    public WebStyledLabel ( final StyleId id )
    {
        this ( id, ( String ) null );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param id   style ID
     * @param icon icon
     */
    public WebStyledLabel ( final StyleId id, final Icon icon )
    {
        this ( id, null, icon );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param id                  style ID
     * @param horizontalAlignment horizontal alignment
     */
    public WebStyledLabel ( final StyleId id, final int horizontalAlignment )
    {
        this ( id, null, horizontalAlignment );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param id                  style ID
     * @param icon                label icon
     * @param horizontalAlignment horizontal alignment
     */
    public WebStyledLabel ( final StyleId id, final Icon icon, final int horizontalAlignment )
    {
        this ( id, null, icon, horizontalAlignment );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param id   style ID
     * @param text text or translation key
     * @param data language data, may not be passed
     */
    public WebStyledLabel ( final StyleId id, final String text, final Object... data )
    {
        this ( id, text, null, data );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param id                  style ID
     * @param text                text or translation key
     * @param horizontalAlignment horizontal alignment
     * @param data                language data, may not be passed
     */
    public WebStyledLabel ( final StyleId id, final String text, final int horizontalAlignment, final Object... data )
    {
        this ( id, text, null, horizontalAlignment, data );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param id   style ID
     * @param text text or translation key
     * @param icon label icon
     * @param data language data, may not be passed
     */
    public WebStyledLabel ( final StyleId id, final String text, final Icon icon, final Object... data )
    {
        this ( id, text, icon, LEADING, data );
    }

    /**
     * Constructs label with the specified preferences.
     *
     * @param id                  style ID
     * @param text                text or translation key
     * @param icon                label icon
     * @param horizontalAlignment horizontal alignment
     * @param data                language data, may not be passed
     */
    public WebStyledLabel ( final StyleId id, final String text, final Icon icon, final int horizontalAlignment, final Object... data )
    {
        super ( LanguageUtils.getInitialText ( text, data ), icon, horizontalAlignment );
        setWrap ( TextWrap.mixed );
        setHorizontalTextAlignment ( LEFT );
        setVerticalTextAlignment ( CENTER );
        setRows ( 0 );
        setMinimumRows ( 0 );
        setMaximumRows ( 0 );
        LanguageUtils.registerInitialLanguage ( this, text, data );
        setStyleId ( id );
    }

    @Override
    public void setText ( final String text )
    {
        // Parsing styles
        final StyledText styledText = getStyledText ( text );

        // Update text
        super.setText ( styledText.getPlainText () );

        // Update styles
        setStyleRanges ( styledText.getStyleRanges () );
    }

    /**
     * Returns styled text used to parse style syntax.
     *
     * @param text text containing style syntax
     * @return styled text used to parse style syntax
     */
    protected StyledText getStyledText ( final String text )
    {
        return new StyledText ( text );
    }

    /**
     * Returns added style ranges.
     *
     * @return added style ranges
     */
    public List<StyleRange> getStyleRanges ()
    {
        return CollectionUtils.copy ( getStyleRangesImpl () );
    }

    /**
     * Adds style range into this label.
     *
     * @param styleRange new style range
     */
    public void addStyleRange ( final StyleRange styleRange )
    {
        final StyleRange removed = addStyleRangeImpl ( styleRange );
        firePropertyChange ( STYLE_RANGES_PROPERTY, removed, styleRange );
    }

    /**
     * Adds style ranges into this label.
     *
     * @param styleRanges new style ranges list
     */
    public void addStyleRanges ( final List<StyleRange> styleRanges )
    {
        addStyleRangesImpl ( styleRanges );
        firePropertyChange ( STYLE_RANGES_PROPERTY, null, styleRanges );
    }

    /**
     * Removes style range from this label.
     *
     * @param styleRange style range to remove
     */
    public void removeStyleRange ( final StyleRange styleRange )
    {
        removeStyleRangeImpl ( styleRange );
        firePropertyChange ( STYLE_RANGES_PROPERTY, styleRange, null );
    }

    /**
     * Removes style ranges from this label.
     *
     * @param styleRanges style ranges to remove
     */
    public void removeStyleRanges ( final List<StyleRange> styleRanges )
    {
        removeStyleRangesImpl ( styleRanges );
        firePropertyChange ( STYLE_RANGES_PROPERTY, styleRanges, null );
    }

    /**
     * Clears all style ranges and adds new ones.
     *
     * @param styleRanges new style ranges
     */
    public void setStyleRanges ( final List<StyleRange> styleRanges )
    {
        clearStyleRangesImpl ();
        addStyleRangesImpl ( styleRanges );
        firePropertyChange ( STYLE_RANGES_PROPERTY, null, styleRanges );
    }

    /**
     * Clears all style ranges.
     */
    public void clearStyleRanges ()
    {
        clearStyleRangesImpl ();
        firePropertyChange ( STYLE_RANGES_PROPERTY, null, null );
    }

    /**
     * Returns added style ranges.
     *
     * @return added style ranges
     */
    protected List<StyleRange> getStyleRangesImpl ()
    {
        if ( styleRanges == null )
        {
            styleRanges = new ArrayList<StyleRange> ( 3 );
        }
        return styleRanges;
    }

    /**
     * Adds style range into this label.
     *
     * @param styleRange new style range
     * @return removed style range
     */
    protected StyleRange addStyleRangeImpl ( final StyleRange styleRange )
    {
        final StyleRange removed = clearSimilarRangeImpl ( styleRange.getStartIndex (), styleRange.getLength () );
        getStyleRangesImpl ().add ( styleRange );
        return removed;
    }

    /**
     * Adds style ranges into this label.
     *
     * @param styleRanges new style ranges list
     */
    protected void addStyleRangesImpl ( final List<StyleRange> styleRanges )
    {
        if ( !CollectionUtils.isEmpty ( styleRanges ) )
        {
            for ( final StyleRange styleRange : styleRanges )
            {
                addStyleRangeImpl ( styleRange );
            }
        }
    }

    /**
     * Removes style range from this label.
     *
     * @param styleRange style range to remove
     */
    protected void removeStyleRangeImpl ( final StyleRange styleRange )
    {
        if ( !getStyleRangesImpl ().remove ( styleRange ) )
        {
            final Iterator<StyleRange> iterator = getStyleRangesImpl ().iterator ();
            while ( iterator.hasNext () )
            {
                final StyleRange range = iterator.next ();
                if ( range.getStartIndex () == styleRange.getStartIndex () && range.getLength () == styleRange.getLength () )
                {
                    iterator.remove ();
                    return;
                }
            }
        }
    }

    /**
     * Removes style ranges from this label.
     *
     * @param styleRanges style ranges to remove
     */
    protected void removeStyleRangesImpl ( final List<StyleRange> styleRanges )
    {
        if ( styleRanges != null )
        {
            for ( final StyleRange styleRange : styleRanges )
            {
                removeStyleRangeImpl ( styleRange );
            }
        }
    }

    /**
     * Clears all style ranges.
     */
    protected void clearStyleRangesImpl ()
    {
        getStyleRangesImpl ().clear ();
    }

    /**
     * Removes any style range found in the same range as the specified one.
     *
     * @param start  range start
     * @param length range length
     * @return removed style range
     */
    protected StyleRange clearSimilarRangeImpl ( final int start, final int length )
    {
        final Iterator<StyleRange> iterator = getStyleRangesImpl ().iterator ();
        while ( iterator.hasNext () )
        {
            final StyleRange range = iterator.next ();
            if ( range.getStartIndex () == start && range.getLength () == length )
            {
                iterator.remove ();
                return range;
            }
        }
        return null;
    }

    /**
     * Returns text wrapping type.
     *
     * @return text wrapping type
     */
    public TextWrap getWrap ()
    {
        return wrap != null ? wrap : TextWrap.none;
    }

    /**
     * Sets text wrapping type.
     *
     * @param wrap text wrapping type
     */
    public void setWrap ( final TextWrap wrap )
    {
        final TextWrap old = this.wrap;
        this.wrap = wrap;
        firePropertyChange ( WRAP_PROPERTY, old, wrap );
    }

    /**
     * Returns horizontal text alignment.
     *
     * @return horizontal text alignment
     */
    public int getHorizontalTextAlignment ()
    {
        return horizontalTextAlignment;
    }

    /**
     * Sets horizontal text alignment.
     *
     * @param alignment horizontal text alignment
     */
    public void setHorizontalTextAlignment ( final int alignment )
    {
        final int old = this.horizontalTextAlignment;
        this.horizontalTextAlignment = alignment;
        firePropertyChange ( HORIZONTAL_TEXT_ALIGNMENT_PROPERTY, old, alignment );
    }

    /**
     * Returns vertical text alignment.
     *
     * @return vertical text alignment
     */
    public int getVerticalTextAlignment ()
    {
        return verticalTextAlignment;
    }

    /**
     * Sets vertical text alignment.
     *
     * @param alignment vertical text alignment
     */
    public void setVerticalTextAlignment ( final int alignment )
    {
        final int old = this.verticalTextAlignment;
        this.verticalTextAlignment = alignment;
        firePropertyChange ( VERTICAL_TEXT_ALIGNMENT_PROPERTY, old, alignment );
    }

    /**
     * Returns amount of rows used to wrap label text.
     *
     * @return amount of rows used to wrap label text
     */
    public int getRows ()
    {
        return rows;
    }

    /**
     * Sets amount of rows used to wrap label text.
     * By default it is set to zero.
     * <p/>
     * Note that it has lower priority than preferred width.
     * If preferred width is set this value is ignored.
     *
     * @param rows amount of rows used to wrap label text
     */
    public void setRows ( final int rows )
    {
        final int old = this.rows;
        this.rows = rows;
        firePropertyChange ( ROWS_PROPERTY, old, rows );
    }

    /**
     * Returns maximum rows amount visible after wrapping.
     *
     * @return maximum rows amount visible after wrapping
     */
    public int getMaximumRows ()
    {
        return maximumRows;
    }

    /**
     * Sets maximum rows amount visible after wrapping.
     * By default it is set to zero.
     *
     * @param maximumRows maximum rows amount visible after wrapping
     */
    public void setMaximumRows ( final int maximumRows )
    {
        final int old = this.maximumRows;
        this.maximumRows = maximumRows;
        firePropertyChange ( MAXIMUM_ROWS_PROPERTY, old, maximumRows );
    }

    /**
     * Returns minimum rows amount visible after wrapping.
     *
     * @return minimum rows amount visible after wrapping
     */
    public int getMinimumRows ()
    {
        return minimumRows;
    }

    /**
     * Sets minimum rows amount visible after wrapping.
     * By default it is set to zero.
     *
     * @param minimumRows minimum rows amount visible after wrapping
     */
    public void setMinimumRows ( final int minimumRows )
    {
        final int old = this.minimumRows;
        this.minimumRows = minimumRows;
        firePropertyChange ( MINIMUM_ROWS_PROPERTY, old, minimumRows );
    }

    @Override
    public StyleId getStyleId ()
    {
        return StyleManager.getStyleId ( this );
    }

    @Override
    public StyleId setStyleId ( final StyleId id )
    {
        return StyleManager.setStyleId ( this, id );
    }

    @Override
    public StyleId resetStyleId ()
    {
        return StyleManager.resetStyleId ( this );
    }

    @Override
    public Skin getSkin ()
    {
        return StyleManager.getSkin ( this );
    }

    @Override
    public Skin setSkin ( final Skin skin )
    {
        return StyleManager.setSkin ( this, skin );
    }

    @Override
    public Skin setSkin ( final Skin skin, final boolean recursively )
    {
        return StyleManager.setSkin ( this, skin, recursively );
    }

    @Override
    public Skin resetSkin ()
    {
        return StyleManager.resetSkin ( this );
    }

    @Override
    public void addStyleListener ( final StyleListener listener )
    {
        StyleManager.addStyleListener ( this, listener );
    }

    @Override
    public void removeStyleListener ( final StyleListener listener )
    {
        StyleManager.removeStyleListener ( this, listener );
    }

    @Override
    public Map<String, Painter> getCustomPainters ()
    {
        return StyleManager.getCustomPainters ( this );
    }

    @Override
    public Painter getCustomPainter ()
    {
        return StyleManager.getCustomPainter ( this );
    }

    @Override
    public Painter getCustomPainter ( final String id )
    {
        return StyleManager.getCustomPainter ( this, id );
    }

    @Override
    public Painter setCustomPainter ( final Painter painter )
    {
        return StyleManager.setCustomPainter ( this, painter );
    }

    @Override
    public Painter setCustomPainter ( final String id, final Painter painter )
    {
        return StyleManager.setCustomPainter ( this, id, painter );
    }

    @Override
    public boolean resetPainter ()
    {
        return StyleManager.resetPainter ( this );
    }

    @Override
    public Shape provideShape ()
    {
        return getWebUI ().provideShape ();
    }

    @Override
    public Insets getMargin ()
    {
        return getWebUI ().getMargin ();
    }

    /**
     * Sets new margin.
     *
     * @param margin new margin
     */
    public void setMargin ( final int margin )
    {
        setMargin ( margin, margin, margin, margin );
    }

    /**
     * Sets new margin.
     *
     * @param top    new top margin
     * @param left   new left margin
     * @param bottom new bottom margin
     * @param right  new right margin
     */
    public void setMargin ( final int top, final int left, final int bottom, final int right )
    {
        setMargin ( new Insets ( top, left, bottom, right ) );
    }

    @Override
    public void setMargin ( final Insets margin )
    {
        getWebUI ().setMargin ( margin );
    }

    @Override
    public Insets getPadding ()
    {
        return getWebUI ().getPadding ();
    }

    /**
     * Sets new padding.
     *
     * @param padding new padding
     */
    public void setPadding ( final int padding )
    {
        setPadding ( padding, padding, padding, padding );
    }

    /**
     * Sets new padding.
     *
     * @param top    new top padding
     * @param left   new left padding
     * @param bottom new bottom padding
     * @param right  new right padding
     */
    public void setPadding ( final int top, final int left, final int bottom, final int right )
    {
        setPadding ( new Insets ( top, left, bottom, right ) );
    }

    @Override
    public void setPadding ( final Insets padding )
    {
        getWebUI ().setPadding ( padding );
    }

    /**
     * Returns Web-UI applied to this class.
     *
     * @return Web-UI applied to this class
     */
    private WebStyledLabelUI getWebUI ()
    {
        return ( WebStyledLabelUI ) getUI ();
    }

    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof WebStyledLabelUI ) )
        {
            try
            {
                setUI ( ( WebStyledLabelUI ) UIManager.getUI ( this ) );
            }
            catch ( final Throwable e )
            {
                Log.error ( this, e );
                setUI ( new WebStyledLabelUI () );
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }

    @Override
    public String getUIClassID ()
    {
        return StyleableComponent.styledlabel.getUIClassID ();
    }

    @Override
    public MouseAdapter onMousePress ( final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMousePress ( this, runnable );
    }

    @Override
    public MouseAdapter onMousePress ( final MouseButton mouseButton, final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMousePress ( this, mouseButton, runnable );
    }

    @Override
    public MouseAdapter onMouseEnter ( final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMouseEnter ( this, runnable );
    }

    @Override
    public MouseAdapter onMouseExit ( final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMouseExit ( this, runnable );
    }

    @Override
    public MouseAdapter onMouseDrag ( final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMouseDrag ( this, runnable );
    }

    @Override
    public MouseAdapter onMouseDrag ( final MouseButton mouseButton, final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMouseDrag ( this, mouseButton, runnable );
    }

    @Override
    public MouseAdapter onMouseClick ( final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMouseClick ( this, runnable );
    }

    @Override
    public MouseAdapter onMouseClick ( final MouseButton mouseButton, final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMouseClick ( this, mouseButton, runnable );
    }

    @Override
    public MouseAdapter onDoubleClick ( final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onDoubleClick ( this, runnable );
    }

    @Override
    public MouseAdapter onMenuTrigger ( final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onMenuTrigger ( this, runnable );
    }

    @Override
    public KeyAdapter onKeyType ( final KeyEventRunnable runnable )
    {
        return EventMethodsImpl.onKeyType ( this, runnable );
    }

    @Override
    public KeyAdapter onKeyType ( final HotkeyData hotkey, final KeyEventRunnable runnable )
    {
        return EventMethodsImpl.onKeyType ( this, hotkey, runnable );
    }

    @Override
    public KeyAdapter onKeyPress ( final KeyEventRunnable runnable )
    {
        return EventMethodsImpl.onKeyPress ( this, runnable );
    }

    @Override
    public KeyAdapter onKeyPress ( final HotkeyData hotkey, final KeyEventRunnable runnable )
    {
        return EventMethodsImpl.onKeyPress ( this, hotkey, runnable );
    }

    @Override
    public KeyAdapter onKeyRelease ( final KeyEventRunnable runnable )
    {
        return EventMethodsImpl.onKeyRelease ( this, runnable );
    }

    @Override
    public KeyAdapter onKeyRelease ( final HotkeyData hotkey, final KeyEventRunnable runnable )
    {
        return EventMethodsImpl.onKeyRelease ( this, hotkey, runnable );
    }

    @Override
    public FocusAdapter onFocusGain ( final FocusEventRunnable runnable )
    {
        return EventMethodsImpl.onFocusGain ( this, runnable );
    }

    @Override
    public FocusAdapter onFocusLoss ( final FocusEventRunnable runnable )
    {
        return EventMethodsImpl.onFocusLoss ( this, runnable );
    }

    @Override
    public MouseAdapter onDragStart ( final int shift, final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onDragStart ( this, shift, runnable );
    }

    @Override
    public MouseAdapter onDragStart ( final int shift, final MouseButton mouseButton, final MouseEventRunnable runnable )
    {
        return EventMethodsImpl.onDragStart ( this, shift, mouseButton, runnable );
    }

    @Override
    public WebCustomTooltip setToolTip ( final String tooltip )
    {
        return TooltipManager.setTooltip ( this, tooltip );
    }

    @Override
    public WebCustomTooltip setToolTip ( final Icon icon, final String tooltip )
    {
        return TooltipManager.setTooltip ( this, icon, tooltip );
    }

    @Override
    public WebCustomTooltip setToolTip ( final String tooltip, final TooltipWay tooltipWay )
    {
        return TooltipManager.setTooltip ( this, tooltip, tooltipWay );
    }

    @Override
    public WebCustomTooltip setToolTip ( final Icon icon, final String tooltip, final TooltipWay tooltipWay )
    {
        return TooltipManager.setTooltip ( this, icon, tooltip, tooltipWay );
    }

    @Override
    public WebCustomTooltip setToolTip ( final String tooltip, final TooltipWay tooltipWay, final int delay )
    {
        return TooltipManager.setTooltip ( this, tooltip, tooltipWay, delay );
    }

    @Override
    public WebCustomTooltip setToolTip ( final Icon icon, final String tooltip, final TooltipWay tooltipWay, final int delay )
    {
        return TooltipManager.setTooltip ( this, icon, tooltip, tooltipWay, delay );
    }

    @Override
    public WebCustomTooltip setToolTip ( final JComponent tooltip )
    {
        return TooltipManager.setTooltip ( this, tooltip );
    }

    @Override
    public WebCustomTooltip setToolTip ( final JComponent tooltip, final int delay )
    {
        return TooltipManager.setTooltip ( this, tooltip, delay );
    }

    @Override
    public WebCustomTooltip setToolTip ( final JComponent tooltip, final TooltipWay tooltipWay )
    {
        return TooltipManager.setTooltip ( this, tooltip, tooltipWay );
    }

    @Override
    public WebCustomTooltip setToolTip ( final JComponent tooltip, final TooltipWay tooltipWay, final int delay )
    {
        return TooltipManager.setTooltip ( this, tooltip, tooltipWay, delay );
    }

    @Override
    public WebCustomTooltip addToolTip ( final String tooltip )
    {
        return TooltipManager.addTooltip ( this, tooltip );
    }

    @Override
    public WebCustomTooltip addToolTip ( final Icon icon, final String tooltip )
    {
        return TooltipManager.addTooltip ( this, icon, tooltip );
    }

    @Override
    public WebCustomTooltip addToolTip ( final String tooltip, final TooltipWay tooltipWay )
    {
        return TooltipManager.addTooltip ( this, tooltip, tooltipWay );
    }

    @Override
    public WebCustomTooltip addToolTip ( final Icon icon, final String tooltip, final TooltipWay tooltipWay )
    {
        return TooltipManager.addTooltip ( this, icon, tooltip, tooltipWay );
    }

    @Override
    public WebCustomTooltip addToolTip ( final String tooltip, final TooltipWay tooltipWay, final int delay )
    {
        return TooltipManager.addTooltip ( this, tooltip, tooltipWay, delay );
    }

    @Override
    public WebCustomTooltip addToolTip ( final Icon icon, final String tooltip, final TooltipWay tooltipWay, final int delay )
    {
        return TooltipManager.addTooltip ( this, icon, tooltip, tooltipWay, delay );
    }

    @Override
    public WebCustomTooltip addToolTip ( final JComponent tooltip )
    {
        return TooltipManager.addTooltip ( this, tooltip );
    }

    @Override
    public WebCustomTooltip addToolTip ( final JComponent tooltip, final int delay )
    {
        return TooltipManager.addTooltip ( this, tooltip, delay );
    }

    @Override
    public WebCustomTooltip addToolTip ( final JComponent tooltip, final TooltipWay tooltipWay )
    {
        return TooltipManager.addTooltip ( this, tooltip, tooltipWay );
    }

    @Override
    public WebCustomTooltip addToolTip ( final JComponent tooltip, final TooltipWay tooltipWay, final int delay )
    {
        return TooltipManager.addTooltip ( this, tooltip, tooltipWay, delay );
    }

    @Override
    public void removeToolTip ( final WebCustomTooltip tooltip )
    {
        TooltipManager.removeTooltip ( this, tooltip );
    }

    @Override
    public void removeToolTips ()
    {
        TooltipManager.removeTooltips ( this );
    }

    @Override
    public void removeToolTips ( final WebCustomTooltip... tooltips )
    {
        TooltipManager.removeTooltips ( this, tooltips );
    }

    @Override
    public void removeToolTips ( final List<WebCustomTooltip> tooltips )
    {
        TooltipManager.removeTooltips ( this, tooltips );
    }

    @Override
    public Dimension getMinimumSize ()
    {
        return getWrap () != TextWrap.none ? new Dimension ( 1, 1 ) : super.getMinimumSize ();
    }

    @Override
    public Dimension getMaximumSize ()
    {
        return getWrap () != TextWrap.none ? new Dimension ( Integer.MAX_VALUE, Integer.MAX_VALUE ) : super.getMaximumSize ();
    }

    @Override
    public void setLanguage ( final String key, final Object... data )
    {
        LanguageManager.registerComponent ( this, key, data );
    }

    @Override
    public void updateLanguage ( final Object... data )
    {
        LanguageManager.updateComponent ( this, data );
    }

    @Override
    public void updateLanguage ( final String key, final Object... data )
    {
        LanguageManager.updateComponent ( this, key, data );
    }

    @Override
    public void removeLanguage ()
    {
        LanguageManager.unregisterComponent ( this );
    }

    @Override
    public boolean isLanguageSet ()
    {
        return LanguageManager.isRegisteredComponent ( this );
    }

    @Override
    public void setLanguageUpdater ( final LanguageUpdater updater )
    {
        LanguageManager.registerLanguageUpdater ( this, updater );
    }

    @Override
    public void removeLanguageUpdater ()
    {
        LanguageManager.unregisterLanguageUpdater ( this );
    }

    @Override
    public WebStyledLabel setPlainFont ()
    {
        return FontMethodsImpl.setPlainFont ( this );
    }

    @Override
    public WebStyledLabel setPlainFont ( final boolean apply )
    {
        return FontMethodsImpl.setPlainFont ( this, apply );
    }

    @Override
    public boolean isPlainFont ()
    {
        return FontMethodsImpl.isPlainFont ( this );
    }

    @Override
    public WebStyledLabel setBoldFont ()
    {
        return FontMethodsImpl.setBoldFont ( this );
    }

    @Override
    public WebStyledLabel setBoldFont ( final boolean apply )
    {
        return FontMethodsImpl.setBoldFont ( this, apply );
    }

    @Override
    public boolean isBoldFont ()
    {
        return FontMethodsImpl.isBoldFont ( this );
    }

    @Override
    public WebStyledLabel setItalicFont ()
    {
        return FontMethodsImpl.setItalicFont ( this );
    }

    @Override
    public WebStyledLabel setItalicFont ( final boolean apply )
    {
        return FontMethodsImpl.setItalicFont ( this, apply );
    }

    @Override
    public boolean isItalicFont ()
    {
        return FontMethodsImpl.isItalicFont ( this );
    }

    @Override
    public WebStyledLabel setFontStyle ( final boolean bold, final boolean italic )
    {
        return FontMethodsImpl.setFontStyle ( this, bold, italic );
    }

    @Override
    public WebStyledLabel setFontStyle ( final int style )
    {
        return FontMethodsImpl.setFontStyle ( this, style );
    }

    @Override
    public WebStyledLabel setFontSize ( final int fontSize )
    {
        return FontMethodsImpl.setFontSize ( this, fontSize );
    }

    @Override
    public WebStyledLabel changeFontSize ( final int change )
    {
        return FontMethodsImpl.changeFontSize ( this, change );
    }

    @Override
    public int getFontSize ()
    {
        return FontMethodsImpl.getFontSize ( this );
    }

    @Override
    public WebStyledLabel setFontSizeAndStyle ( final int fontSize, final boolean bold, final boolean italic )
    {
        return FontMethodsImpl.setFontSizeAndStyle ( this, fontSize, bold, italic );
    }

    @Override
    public WebStyledLabel setFontSizeAndStyle ( final int fontSize, final int style )
    {
        return FontMethodsImpl.setFontSizeAndStyle ( this, fontSize, style );
    }

    @Override
    public WebStyledLabel setFontName ( final String fontName )
    {
        return FontMethodsImpl.setFontName ( this, fontName );
    }

    @Override
    public String getFontName ()
    {
        return FontMethodsImpl.getFontName ( this );
    }
}