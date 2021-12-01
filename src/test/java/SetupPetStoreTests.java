import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import static utils.Log.LOG;

public class SetupPetStoreTests {

    @BeforeEach
    public void setUp(TestInfo testInfo) {

        LOG.info("\n_____ Start of '{}' test. _____", testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {

        LOG.info("\n_____ Finish of '{}' test. _____", testInfo.getDisplayName());
    }
}
