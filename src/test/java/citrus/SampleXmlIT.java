package citrus;

import com.consol.citrus.annotations.CitrusXmlTest;
import com.consol.citrus.testng.AbstractTestNGCitrusTest;
import org.testng.annotations.Test;

/**
 * This is a sample Citrus integration test for loading XML syntax test case.
 *
 * @author Citrus
 */
@Test
public class SampleXmlIT extends AbstractTestNGCitrusTest {

    @CitrusXmlTest(name = "SampleXmlIT")
    public void sampleXml() {}
}
