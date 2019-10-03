import javax.xml.xpath.*
import javax.xml.parsers.DocumentBuilderFactory

def main() {
    // session to get the upstream flowfile
    // NOTE: you can also use session.get(X) 
    // to retrieve 'X' number of flowfiles
    // from the session
    def flowFile = session.get()
    if (flowFile == null) {
        return
    }

    def inputStream
    try {

        // reading flowfile content into inputstream
        inputStream = session.read(flowFile)
        // xpath handler
        def xpath = XPathFactory.newInstance().newXPath()
        // xml document instance
        def builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        // parse given xml document
        def records = builder.parse(inputStream).documentElement
        // get  flowfile attribute named 'id'
        def id =  flowFile.getAttribute("id");
        // retrieving value of lastname node
        def lastname = xpath.evaluate( "/employees/employee[$id]/name/lastname", records )
        //println "lastname : $lastname"
        // write result as flowfile attribute
        flowFile = session.putAttribute(flowFile, 'lastname', lastname)
        // transfer incoming flowfile to success
        session.transfer(flowFile, ExecuteScript.REL_SUCCESS)
    } catch (e) {
        log.error("Error evaluating xpath expression", e)
        // transfer incoming flowfile to success
        session.transfer(flowFile, ExecuteScript.REL_FAILURE)
    } finally {
        // close opened inputstream
        if (inputStream != null) {
            inputStream.close()
        }
    }
}

main()