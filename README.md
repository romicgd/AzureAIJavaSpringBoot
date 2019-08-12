# Java Spring Boot Example integrating with Azure Application Insights
Azure Java Spring boot Example integrating with Application Insights

Additional info is available at

[Azure App Service - logging and monitoring made easy](https://www.cloudmatter.io/home/azure-app-service-log-monitoring-made-easy)

[Azure App Service - monitoring application behavior (and using custom metrics)](https://www.cloudmatter.io/home/azure-app-service-monitoring-application-behavior-and-using-custom-metrics)


## Setup
Configure your target App Service in <code>pom.xml</code>

```xml
<resourceGroup>...</resourceGroup>
<appName>...</appName>
<subscriptionId>...</subscriptionId>
```
   

Set APPINSIGHTS_INSTRUMENTATIONKEY in your Azure App Service Application settings
![APPINSIGHTS_INSTRUMENTATIONKEY](./src/common/images/ApplicationSettingsAIKey.png "APPINSIGHTS_INSTRUMENTATIONKEY")


Provide log message in your Azure App Service Application settings
![LogMessage](./src/common/images/LogMessage.png "LogMessage")

Configure your Azure deployment credentials on maven <code>settings.xml</code>

```xml
	<server>
		<id>azure-auth</id>
		<configuration>
			<client>...</client>    
			<tenant>...</tenant>
			<key>...</key>
			<environment>AZURE</environment>
	  </configuration>
    </server> 
```

Configure proxy (fi required)
As you configure your Java project for ApplicationInsights to allow maven download dependencies you can set maven proxies in your settings.xml (e.g. in C:\Users\%myusername%\.m2)

```xml
  <proxies>
	 <proxy>
	  <id>mgsproxy</id>
	  <active>true</active>
	  <protocol>http</protocol>
	  <host>xx.xx.xx.xx</host>
	  <port>nnnn</port>
	</proxy>
  </proxies>
```


Deploy your application and Monitor your log messages and metrics in Azure Application Insights

![QueryLogs](./src/common/images/QueryLogs.png "QueryLogs")

![CustomMetricsQuery](./src/common/images/CustomMetricsQuery.png "CustomMetricsQuery")

![CustomMetricsGraphs](./src/common/images/CustomMetricsGraph.png "CustomMetricsGraph")


## Query Examples
1. Find recent log messages with specific sub-string:
```
traces | where timestamp > ago(1h) | where message contains 'build' | sort by timestamp desc
```

2. Find count of recent request for each error HTTP code:
```
requests | where timestamp > ago(1h) | summarize count() by resultCode
```

3. Find recent http errors:
```
requests | where timestamp > ago(1h) | 
```

4. Find recent exceptions for specific app (can use cloud_RoleName as filter):
```
exceptions | where timestamp > ago(1h) | where cloud_RoleName  == 'springapitest0003' 
| sort by timestamp desc | project problemId
```