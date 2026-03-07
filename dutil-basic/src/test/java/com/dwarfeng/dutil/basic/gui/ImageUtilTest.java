package com.dwarfeng.dutil.basic.gui;

import com.dwarfeng.dutil.basic.gui.awt.CommonIconLib;
import com.dwarfeng.dutil.basic.gui.awt.ImageSize;
import com.dwarfeng.dutil.basic.gui.awt.ImageUtil;
import com.dwarfeng.dutil.basic.gui.awt.Size2D;
import com.dwarfeng.dutil.basic.num.IntNumberValue;
import com.dwarfeng.dutil.basic.num.NumberValue;
import com.dwarfeng.dutil.basic.num.OutOfIntervalException;
import com.dwarfeng.dutil.basic.str.DefaultName;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageUtilTest {

    static final class InvalidImageSize2D implements Size2D {

        public static final InvalidImageSize2D INSTANCE = new InvalidImageSize2D();

        private InvalidImageSize2D() {
        }

        @Override
        public NumberValue getHeight() {
            return new IntNumberValue(0);
        }

        @Override
        public NumberValue getWidth() {
            return new IntNumberValue(-1);
        }
    }

    @Deprecated
    @Test
    public void testDefaultImage() {
        assertEquals(128, ImageUtil.defaultImage().getWidth(null));
        assertEquals(128, ImageUtil.defaultImage().getHeight(null));
    }

    @Deprecated
    @Test
    public void testDefaultImageImageSize() {
        assertEquals(ImageSize.ICON_SMALL.getIntWidth(), ImageUtil.defaultImage(ImageSize.ICON_SMALL).getWidth(null));
        assertEquals(ImageSize.ICON_SMALL.getIntHeight(), ImageUtil.defaultImage(ImageSize.ICON_SMALL).getHeight(null));
    }

    @Test
    public void testGetDefaultImage() {
        assertEquals(128, ImageUtil.getDefaultImage().getWidth(null));
        assertEquals(128, ImageUtil.getDefaultImage().getHeight(null));
    }

    @Test
    public void testGetDefaultImageSize2D() {
        assertEquals(ImageSize.ICON_SMALL.getIntWidth(),
                ImageUtil.getDefaultImage(ImageSize.ICON_SMALL).getWidth(null));
        assertEquals(ImageSize.ICON_SMALL.getIntHeight(),
                ImageUtil.getDefaultImage(ImageSize.ICON_SMALL).getHeight(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDefaultImageSize2D1() {
        ImageUtil.getDefaultImage(InvalidImageSize2D.INSTANCE);
    }

    @Test
    public void testGetInternalImageName() {
        assertNotNull(ImageUtil.getInternalImage(CommonIconLib.UNKNOWN_BLACK));
    }

    @Test
    public void testGetInternalImageNameImageSize2D() {
        assertNull(ImageUtil.getInternalImage(new DefaultName("png"), null, null));
        assertNull(ImageUtil.getInternalImage(new DefaultName("png"), null, ImageSize.ICON_SMALL));
        assertNotNull(
                ImageUtil.getInternalImage(new DefaultName("png"), ImageUtil.getDefaultImage(), ImageSize.ICON_SMALL));
        assertEquals(ImageSize.ICON_SMALL.getIntWidth(),
                ImageUtil.getInternalImage(new DefaultName("png"), ImageUtil.getDefaultImage(), ImageSize.ICON_SMALL)
                        .getWidth(null));
        assertEquals(ImageSize.ICON_SMALL.getIntHeight(),
                ImageUtil.getInternalImage(new DefaultName("png"), ImageUtil.getDefaultImage(), ImageSize.ICON_SMALL)
                        .getHeight(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInternalImageNameImageSize2D1() {
        ImageUtil.getInternalImage(new DefaultName("png"), ImageUtil.getDefaultImage(), InvalidImageSize2D.INSTANCE);
    }

    @Test
    public void testIsImageSize2D() {
        assertTrue(ImageUtil.isImageSize2D(ImageSize.ICON_LARGE));
        assertFalse(ImageUtil.isImageSize2D(InvalidImageSize2D.INSTANCE));
    }

    @Test
    public void testNewImageSize2DImage() {
        Size2D size = ImageUtil.newImageSize2D(ImageUtil.getDefaultImage());
        assert size != null;
        assertEquals(128, size.getIntWidth());
        assertEquals(128, size.getIntHeight());
    }

    @Test
    public void testNewImageSize2DIntInt() {
        Size2D size = ImageUtil.newImageSize2D(128, 64);
        assertEquals(128, size.getIntWidth());
        assertEquals(64, size.getIntHeight());
    }

    @Test(expected = OutOfIntervalException.class)
    public void testNewImageSize2DIntInt1() {
        ImageUtil.newImageSize2D(0, 0);
    }

    @Test(expected = OutOfIntervalException.class)
    public void testNewImageSize2DIntInt2() {
        Size2D size = ImageUtil.newImageSize2D(-1, 1);
        assertEquals(128, size.getIntWidth());
        assertEquals(64, size.getIntHeight());
    }

    @Test(expected = OutOfIntervalException.class)
    public void testNewImageSize2DIntInt3() {
        Size2D size = ImageUtil.newImageSize2D(2, -1);
        assertEquals(128, size.getIntWidth());
        assertEquals(64, size.getIntHeight());
    }

    @Test(expected = OutOfIntervalException.class)
    public void testNewImageSize2DIntInt4() {
        Size2D size = ImageUtil.newImageSize2D(2, 0);
        assertEquals(128, size.getIntWidth());
        assertEquals(64, size.getIntHeight());
    }

    @Test(expected = OutOfIntervalException.class)
    public void testNewImageSize2DIntInt5() {
        Size2D size = ImageUtil.newImageSize2D(0, 2);
        assertEquals(128, size.getIntWidth());
        assertEquals(64, size.getIntHeight());
    }

    @Test
    public void testRequireImageSize2DSize2D() {
        ImageUtil.requireImageSize2D(ImageSize.ICON_LARGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireImageSize2DSize2D1() {
        ImageUtil.requireImageSize2D(InvalidImageSize2D.INSTANCE);
    }

    @Test
    public void testRequireImageSize2DSize2DString() {
        ImageUtil.requireImageSize2D(ImageSize.ICON_LARGE, "1233211234567");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireImageSize2DSize2DString1() {
        ImageUtil.requireImageSize2D(InvalidImageSize2D.INSTANCE, "1233211234567");
    }

    @Ignore
    @Test
    public void testScaleAndOverlayImage() {
        fail("Not yet implemented"); // TODO 如何实现该测试。
    }
}
