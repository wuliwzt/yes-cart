package org.yes.cart.service.image.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yes.cart.constants.Constants;
import org.yes.cart.constants.ServiceSpringKeys;
import org.yes.cart.service.domain.impl.BaseCoreDBTestCase;
import org.yes.cart.service.image.ImageNameStrategy;

import java.util.ArrayList;
import java.util.List;

/**
* User: Igor Azarny iazarny@yahoo.com
 * Date: 09-May-2011
 * Time: 14:12:54
 */
public class ProductImageNameStrategyImplTest extends BaseCoreDBTestCase {

    private ImageNameStrategy imageNameStrategy;

    private String [] fileNames = {
            "some_seo_image_file-name_PRODUCT-or-SKU-CODE_a.jpeg",
            "some_seo_image_file-name_PRODUCT1_b.jpeg",
            "some_seo_image_file-name_PROD+UCT1_c.jpeg",
            "some_seo_image_file_name_КОД-ПРОДУКТА_d.jpeg",
            "some-seo-image-file-name_ЕЩЕ-КОД-пРОДУКТА_e.jpg",
            "очень-вкусная-сосиска-с-камнями-от-Сваровски_-КОД-Сосики-_f.jpg",
            "Очень-Вкусная-Сосиска3-с-камнями-от-Булыжникова_ЕЩЕ-КОД-ПРОДУКТА!_g.jpg"
    };



    @Before
    public void setUp() throws Exception {
        super.setUp();
        imageNameStrategy = (ImageNameStrategy)ctx.getBean(ServiceSpringKeys.PRODUCT_IMAGE_NAME_STRATEGY);
    }

    @After
    public void tearDown() {
        imageNameStrategy = null;
        super.tearDown();
    }

    @Test
    public void testGetFileName() {

        assertEquals("1261644759_627724_russkaya-magiya.jpg",
                imageNameStrategy.getFileName("posts/2009-12/1261644759_627724_russkaya-magiya.jpg"));

        assertEquals("1261644759_627724_russkaya-magiya.jpg",
                imageNameStrategy.getFileName("posts/2009-12/1261644759_627724_russkaya-magiya.jpg?w=10&h=4"));

        assertEquals("1261644759_627724_russkaya-magiya.jpg",
                imageNameStrategy.getFileName("posts/2009-12/1261644759_627724_russkaya-magiya.jpg?w=10&h=4"));

        assertEquals("1261644759_627724_russkaya-magiya.jpg",
                imageNameStrategy.getFileName("posts/2009-12/1261644759_627724_russkaya-magiya.jpg?w=10&h=4~!@#$%^&*()_+"));


    }


    @Test
    public void testGetCode() {

        List<String> expectation = new ArrayList<String>();
        expectation.add("PRODUCT-or-SKU-CODE");
        expectation.add("PRODUCT1");
        expectation.add("PROD+UCT1");
        expectation.add("КОД-ПРОДУКТА");
        expectation.add("ЕЩЕ-КОД-пРОДУКТА");
        expectation.add("-КОД-Сосики-");
        expectation.add("ЕЩЕ-КОД-ПРОДУКТА!");

        for (String fileName : fileNames) {
            String code = imageNameStrategy.getCode(fileName);
            assertNotNull(code);
            assertFalse("Contains _ ", code.indexOf('_') > -1);
            assertTrue(fileName + " not in expectations", expectation.contains(code));
            assertTrue(expectation.remove(code));
        }

        assertTrue(expectation.isEmpty());


        assertEquals("code",
                imageNameStrategy.getCode("seo_name_code_1.jpg"));
        assertEquals("code",
                imageNameStrategy.getCode("_code_1.jpg"));
        assertEquals("code",
                imageNameStrategy.getCode("code_1.jpg"));

        //test case to support file names without product or sku code

        assertEquals("SOBOT",
                imageNameStrategy.getCode("sobot-picture.jpeg"));



    }


    @Test
    public void testGetCodeForIncorrectFileName() {

        assertEquals(Constants.NO_IMAGE, imageNameStrategy.getCode(null));
        assertEquals(Constants.NO_IMAGE, imageNameStrategy.getCode(""));
        assertEquals(Constants.NO_IMAGE, imageNameStrategy.getCode("some-incorrect-code-in-file-name"));

    }


    

}