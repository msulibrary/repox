package pt.utl.ist.repox.dataSource;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.dom4j.DocumentException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.utl.ist.accessPoint.manager.DefaultAccessPointsManager;
import pt.utl.ist.configuration.ConfigSingleton;
import pt.utl.ist.configuration.DefaultRepoxContextUtil;
import pt.utl.ist.dataProvider.DataProvider;
import pt.utl.ist.dataProvider.DataSource;
import pt.utl.ist.dataProvider.LightDataManager;
import pt.utl.ist.metadataTransformation.MetadataFormat;
import pt.utl.ist.util.exceptions.AlreadyExistsException;
import pt.utl.ist.util.exceptions.InvalidArgumentsException;
import pt.utl.ist.util.exceptions.ObjectNotFoundException;
import pt.utl.ist.util.exceptions.task.IllegalFileFormatException;

public class HarvestOaiTest {
    private final String DATA_PROVIDER_ID = "DP_OAI";
    private final String DATA_SOURCE_ID = "DS_OAI";
    private final String DATA_SOURCE_DESCRIPTION = "DS_description";
    private final String SOURCE_URL = "http://bd1.inesc-id.pt/repoxel/OAIHandler";
    private final String SOURCE_SET = "bmfinancas";
    private final String SOURCE_SCHEMA = "http://www.europeana.eu/schemas/ese/ESE-V3.3.xsd";
    private final String SOURCE_NAMESPACE = "http://www.europeana.eu/schemas/ese/";
    private final String SOURCE_METADATA_FORMAT = MetadataFormat.ese.name();
    private final int RECORD_COUNT = 37;

    LightDataManager dataManager;
    private DataProvider provider;
    private DataSource dataSourceOai;

    @Before
    public void setUp() {
        try {
            ConfigSingleton.setRepoxContextUtil(new DefaultRepoxContextUtil());
            dataManager = (LightDataManager)ConfigSingleton.getRepoxContextUtil().getRepoxManagerTest().getDataManager();

            provider = dataManager.createDataProvider(DATA_PROVIDER_ID, "pt", "DP_description");
            dataSourceOai = dataManager.createDataSourceOai(provider.getId(), DATA_SOURCE_ID, DATA_SOURCE_DESCRIPTION,
                    SOURCE_SCHEMA, SOURCE_NAMESPACE, SOURCE_METADATA_FORMAT, SOURCE_URL, SOURCE_SET, null, null, null);

        }
        catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws IOException, DocumentException, ClassNotFoundException, NoSuchMethodException, IllegalFileFormatException, SQLException, ParseException, ObjectNotFoundException {
        dataManager.deleteDataProvider(provider.getId());
    }

    @Test
    public void testRun() {
        try {
            File logFile = new File(ConfigSingleton.getRepoxContextUtil().getRepoxManagerTest().getConfiguration().getTempDir() + "/log.txt");

            dataSourceOai.ingestRecords(logFile, true);
            DefaultAccessPointsManager accessPointsManager = (DefaultAccessPointsManager)ConfigSingleton.getRepoxContextUtil().getRepoxManagerTest().getAccessPointsManager();
            int[] recordCountLastrowPair = accessPointsManager.getRecordCountLastrowPair(dataSourceOai, null, null, null);
            int recordCount = recordCountLastrowPair[0];
            Assert.assertEquals(RECORD_COUNT, recordCount);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
/*
	@Test
	public void testRunFromFile() {
		Assert.assertTrue(false);
	}
*/
}
