/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.pondskum.gui.swing.notifyer;

import com.googlecode.pinthura.annotation.SuppressionReason;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class FontedTextDimensionBuilder {

    private float width;
    private float height;
    private float ascent;
    private float descent;

    public FontedTextDimensionBuilder withWidth(final float width) {
        this.width = width;
        return this;
    }

    public FontedTextDimensionBuilder withHeight(final float height) {
        this.height = height;
        return this;
    }

    public FontedTextDimensionBuilder withAscent(final float ascent) {
        this.ascent = ascent;
        return this;
    }

    public FontedTextDimensionBuilder withDescent(final float descent) {
        this.descent = descent;
        return this;
    }

    public FontedTextDimension build() {
        FontedTextDimension fontedTextDimension = new FontedTextDimension();
        fontedTextDimension.setHeight(height);
        fontedTextDimension.setWidth(width);
        fontedTextDimension.setAscent(ascent);
        fontedTextDimension.setDescent(descent);
        return fontedTextDimension;
    }
}
