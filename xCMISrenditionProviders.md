_This document refers to 1.1.0 version of xCMIS._

# Introduction #

xCMIS fully supports the renditions mechanism for creating content thumbnails and previews. Covering all possible content-types are so complex, so we decided to made a possibility
to implement easily you own rendition provider for the content-type you want to be available to preview.


# Details #

Typical rendition provider implements the org.xcmis.spi.RenditionProvider interface and consists of the following methods:

  * **MimeType[.md](.md) getSupportedMediaType()** - this method returns an array of mime-types, whose current provider can handle. For example, PDF rendition provider declares this array as
```
private static final MimeType[] SUPPORTED_MEDIA_TYPES = new String[]{"application/pdf"};
```

  * **MimeType getProducedMediaType()** - this method returns an mime type, whose current provider can produce.
```
private static final MimeType PRODUCED = new MimeType("image", "png");
```

  * **ContentStream getRenditionStream()** - main method which generate content of thumbnails and previews. RenditionContentStream contains byte array for file content, filename, media-type and rendition kind.

  * **int getHeight()** - returns height of generated renditions;

  * **int getWidth()** - returns width of generated renditions;

  * **String getKind()** - king of generated rendition;

So, let's see how may look like simple RenditionProvider, for example, making DjVu renditions:
```
public class DjVuRenditionProvider implements RenditionProvider
{
   private static final MimeType[] SUPPORTED_MEDIA_TYPES = new String[]{"image/x.djvu"}; 

   private static final MimeType PRODUCED = new MimeType("image", "png");

  // Defining mime-type accepted by provider;
   public MimeType[] getSupportedMediaType()
   {
      return SUPPORTED_MEDIA_TYPES;
   }

  // Defining mime-type produced by provider;
   public MimeType getProducedMediaType()
   {
      return PRODUCED;
   }

   public ContentStream getRenditionStream(ContentStrean stream) throws IOException, RepositoryException
   {
      DJVUDocument doc = null;
      try
      {
         doc = DJVUReader.load(stream.getStream());
         DjVuPage page = (DjVuPage )doc.getDocumentCatalog().getAllPages().get(0);
         BufferedImage image = page.convertToImage();
 
         //Creating thumbnail image from the first page of the document

         int height = image.getHeight() / scale;
         int width = image.getWidth() / scale;
         BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

          ....(skipped image preparations).... 

         ByteArrayOutputStream out = new ByteArrayOutputStream();
         ImageIO.write(scaledImage, "png", out);
         ContentStream renditionStream =
            new BaseContentStream(out.toByteArray(), "filename", new MimeType("image/png"));
         renditionStream.setHeight(height);
         renditionStream.setWidth(width);
         return renditionStream;
      }
      finally
      {
            doc.close();
      }
   }

   public int getHeight()
   {
      return 100;
   }

   public int getWidth()
   {
      return 100;
   }


   public String getKind()
   {
      return "cmis:thumbnail";
   }

```

As a result, we need to return an CmisRenditionType object, so take a look on a example of using providers & creating such element:
```
 RenditionProvider renditionProvider = new DjVuRenditionProvider();
 ContentStream renditionContentStream = renditionProvider.getRenditionStream(entry.getContent(null));

 String id = entry.getObjectId();
 CmisRenditionType rendition = new CmisRenditionType();
 rendition.setStreamId(id);
 rendition.setKind(renditionProvider.getKind());
 rendition.setMimetype(renditionProvider.getProducedMediaType().toString());
 rendition.setLength(BigInteger.valueOf(renditionContentStream.length()));
 rendition.setHeight(renditionProvider.getHeight());
 rendition.setWidth(renditionProvider.getWidth());
```


### Adding rendition providers ###

Adding rendition providers is simple. To configure them, you need to add optional parameter called "renditionProviders" into your CmisRegistry component configuration, commonly located at WEB-INF/classes/conf/configuration.xml. This parameter must contain list of provider's classes FQNs to add. Example configuration see below:
```
<!-- Storage Provider -->
<component>
		<type>org.xcmis.spi.deploy.ExoContainerCmisRegistry</type>
                 <init-params>
			<values-param>
				<name>renditionProviders</name>
				<description>Redition providers classes.</description>
				<value>org.xcmis.renditions.impl.DjVuRenditionProvider</value>
				<value>org.xcmis.renditions.impl.ImageRenditionProvider</value>
			</values-param>
		</init-params>
	</component>
```