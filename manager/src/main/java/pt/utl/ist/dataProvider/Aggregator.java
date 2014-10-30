package pt.utl.ist.dataProvider;import java.io.IOException;import java.util.ArrayList;import java.util.List;import javax.xml.bind.annotation.XmlAccessType;import javax.xml.bind.annotation.XmlAccessorType;import javax.xml.bind.annotation.XmlElement;import javax.xml.bind.annotation.XmlRootElement;import javax.xml.bind.annotation.XmlTransient;import org.dom4j.DocumentException;import org.dom4j.DocumentHelper;import org.dom4j.Element;import pt.utl.ist.configuration.ConfigSingleton;import com.wordnik.swagger.annotations.ApiModel;import com.wordnik.swagger.annotations.ApiModelProperty;/** * Aggregator data type * @author Emanuel * @author Simon Tzanakis (Simon.Tzanakis@theeuropeanlibrary.org) */@XmlRootElement(name = "aggregator")@XmlAccessorType(XmlAccessType.NONE)@ApiModel(value = "An Aggregator")public class Aggregator {    //    private static final Logger log = Logger.getLogger(Aggregator.class);    @XmlElement    @ApiModelProperty(position = 1)    private String                    id;    @XmlElement    @ApiModelProperty(position = 2, required = true)    private String                    name;    @XmlElement    @ApiModelProperty(position = 3)    private String                    nameCode;    @XmlElement    @ApiModelProperty(position = 4)    private String                    homepage;    @XmlTransient    private List<DataProvider> dataProviders = new ArrayList<DataProvider>();    /**     * Get the Identifier of the Aggregator. The Identifier value is generated by the server side application. If     * an error occurs on the server side (e.g. "Aggregator Already Exists"), the default value (-1L) is returned.     *     * @return - Long     */    public String getId() {        return id;    }    /**     * Set the Identifier of the Aggregator. This method is used by the Server side application. A value set by the     * client side application is ignored.     *     * @param id - Long     */    public void setId(String id) {        this.id = id;    }    /**     * Get the Name of the Aggregator     *     * @return - String     */    public String getName() {        return name;    }    /**     * Set the Name of the Aggregator     *     * @param name - String     */    public void setName(String name) {        this.name = name;    }    /**     * Get the code associated to the Aggregator. The NameCode is used by the server side application     * to generate a unique file identifier for harversted metadata.     *     * @return - String     */    public String getNameCode() {        return nameCode;    }    /**     * Set the code associated to the Aggregator. The NameCode is used by the server side application     * to generate a unique file identifier for harversted metadata.     *     * @return - String     */    public void setNameCode(String nameCode) {        this.nameCode = nameCode;    }    /**     * Get the URL of the Aggregator's Home Page     *     * @return URL     */    public String getHomepage() {        return homepage;    }    //    /**    //     * Get the URL of the Aggregator's Home Page    //     *    //     * @return URL    //     */    //    public URL getHomePageUrl() {    //        try {    //            return new URL(homePage);    //        } catch (MalformedURLException e) {    //            throw new RuntimeException("Cannot create url from '" + homePage + "'!", e);    //        }    //    }    /**     * Set the URL of the Aggregator's Home Page     *     * @param homepage - URL     */    public void setHomepage(String homepage) {        this.homepage = homepage;    }    /**     * Get the List of Providers belonging to this Aggregator instance.     *     * @return -  List<DataProvider>     * @see {@link DataProvider}     */    public List<DataProvider> getDataProviders() {        return dataProviders;    }    /**     * Set the List of Providers belonging to this Aggregator instance.     *     * @param dataProviders - List<DataProvider>     * @see {@link DataProvider}     */    public void setDataProviders(List<DataProvider> dataProviders) {        this.dataProviders.addAll(dataProviders);    }    /**     * Add a Data Provider     *     * @param dataProvider - DataProvider     * @see {@link DataProvider}     */    public void addDataProvider(DataProvider dataProvider) {        dataProviders.add(dataProvider);    }    /**     * Generate an Id for Aggregator     * @param name     * @return a generated String by using the provided String     * @throws DocumentException     * @throws IOException     */    public static String generateId(String name) throws DocumentException, IOException {        String generatedIdPrefix = "";        for (int i = 0; (i < name.length() && i < 32); i++) {            if ((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') || (name.charAt(i) >= '0' && name.charAt(i) <= '9')) {                generatedIdPrefix += name.charAt(i);            }        }        generatedIdPrefix += "r";        return generatedIdPrefix + generateNumberSufix(generatedIdPrefix);    }    private static int generateNumberSufix(String basename) throws DocumentException, IOException {        int currentNumber = 0;        String currentFullId = basename + currentNumber;        while (((DefaultDataManager)ConfigSingleton.getRepoxContextUtil().getRepoxManager().getDataManager()).getAggregator(currentFullId) != null) {            currentNumber++;            currentFullId = basename + currentNumber;        }        return currentNumber;    }    public Aggregator() {    }    public Aggregator(String id, String name, String nameCode, String homepage, List<DataProvider> dataProviders) {        this.id = id;        this.name = name;        this.nameCode = nameCode;        this.homepage = homepage;        this.dataProviders = dataProviders;    }    /**     * Create Element from aggregator information     * @param writeDataProviders      * @return Document     */    public Element createElement(boolean writeDataProviders) {        Element aggregatorNode = DocumentHelper.createElement("aggregator");        aggregatorNode.addAttribute("id", this.getId());        aggregatorNode.addElement("name").setText(this.getName());        if (this.getNameCode() != null) {            aggregatorNode.addElement("nameCode").setText(this.getNameCode());        }        if (this.getHomepage() != null) {            aggregatorNode.addElement("url").setText(this.getHomepage().toString());        }        if (writeDataProviders && this.getDataProviders() != null) {            for (DataProvider dataProvider : this.getDataProviders()) {                aggregatorNode.add(dataProvider.createElement(true));            }        }        return aggregatorNode;    }}