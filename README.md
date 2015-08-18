# Appnexus OAS Java Client #

Appnexus OAS Java Client is a wrapper which operates on top of OAS web services and simplifies accessing OAS services.

## Installing

### With Maven

You can use Maven by including the library:

```xml
<dependency>
    <groupId>eu.adlogix.oas</groupId>
    <artifactId>appnexus-oas-java-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Initialization

### OasServiceFactory needs to be initialized with credentials as shown below
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
````

## Accessing Services
All the Oas Services can be accessed using the OasServiceFactory. 

Example of retrieving the CampaignService is shown below.
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
CampaignService service = factory.getCampaignService();
````

### Campaign Service
CampaignService provides all the campaign related operations such as getCampaignById, addCampaign and updateCampaign.

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
CampaignService service = factory.getCampaignService();
Campaign campaign = service.getCampaignById("testCampaignId");
````

### Campaign Group Service
CampaignGroupService provides campaign group related operations such as createGroup.

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
CampaignGroupService campaignGroupService = factory.getCampaignGroupService();
CampaignGroup group = new CampaignGroup();
group.setId("testCampaignGrpGuni10");
group.setDescription("test");
campaignGroupService.createGroup(group);
````

### Competitive Category Service
CompetitiveCategoryService provides operations such as getAllCompetitiveCategories and addCompetitiveCategory.

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
CompetitiveCategoryService service = factory.getCompetitiveCategoryService();
List<CompetitiveCategory> list = service.getAllCompetitiveCategories();
````

### Creative Service
CreativeService provides creative related operations such as createCreative.

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
CreativeService service = factory.getCreativeService();
Creative creative = new Creative();
creative.setId("GuniCreative_001");
creative.setCampaignId("test_campaign_055");
creative.setName("Api Creative");
service.createCreative(creative);
````

### Insertion Order Service
InsertionOrderService provides all insertion order related operations such as addInsertionOrder, updateInsertionOrder and getInsertionOrderById

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
InsertionOrderService service = factory.getInsertionOrderService();
InsertionOrder insertionOrder = service.getInsertionOrderById("test_insertionorder_01");
````

### Network Service
NetworkService provides all network related operations that deal with sites, pages, sections, positions and companion positions.

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
NetworkService service = factory.getNetworkService();
List<Site> list = service.getAllSites();
````

### Product Service
ProductService provides all product related operations such as createProduct and getProduct

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
ProductService service = factory.getProductService();
Product product = service.getProduct("TestProduct");
````

### Report Service
ReportService provides functions for retrieving delivery reports.

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
ReportService service = factory.getReportService();
CampaignDeliveryByPageAndPosition delivery = service.getCampaignDeliveryByPageAndPosition("test_campaign_id", DATE_FORMATTER.parseDateTime("2012-02-27"), DATE_FORMATTER.parseDateTime("2012-02-28"));
````

### Targeting Service
TargetingService provides fuctions for retrieving targeting details.

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
TargetingService service = factory.getTargetingService();
List<TargetingCodeData> list = service.getTargetingCodeDataLists(TargetingCode.DEVICE_GROUP);
````

### Advertiser Service
AdvertiserService provides all advertiser related fuctions such as addAdvertiser,updateAdvertiser,getAdvertiserById and getAllAdvertisers;

Example
```java
Credentials credentials = Credentials(host,userName, password, account);
OasServiceFactory factory = new OasServiceFactory(credentials);
AdvertiserService service = factory.getAdvertiserService();
List<Advertiser> advertiserList = service.getAllAdvertisers();
````




