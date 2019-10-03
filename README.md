# nifi-evaluateXpath-groovy
EvaluateXpath Processor within Apache NiFi doesn't support Expression Language in xpaths expressions. The processor has been designed to work on flowfiles in batches where the hardcoded xpath expressions can be evaluated directly rather than dynamic content-based xpath expressions against each flowfile which is much expensive operation to perform and may degrade the performance of the overall dataflow. 

Hence, a workaround is implemented in groovy language which can be copied to ExecuteScript Processor for evaluating runtime xpaths built from attribute content recieve from upstream.

## How to do
1. If you are evaluating single xpath then you can import template to your instance. Note that you can also extend the exiting groovy script implementation to add more evaluations based on your use-case.

2. Since, xpath expression contains attribute which is referenced at runtime, you need to have the attribute in your upstream flow or you can simply create it with UpdateAttribute Processor mplemented in the template. 

3. Copy sample xml payload [employee.xml] to GenerateFlowFile Processor for outputting xml as flowfile content which is then evaluated aganist xpath expression replaced with attribute value