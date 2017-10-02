# Live Data Streaming using PDI and CTools Dashboards (MQTT)

Run transformations:
	- iot-device-emulator
	- streaming-using-mqtt
	
Upload dashboard to Pentaho server and run the dashboards. Click on "Connect" button.

Note: must be connected to internet

02/10/2017 
	- Samples were updated to use Pentaho Marketplace MQTT steps instad of custom code in UDJC
	- Updated MQTT libraries in Dashboard 
	- Updated code in dashboard
	- No need to start an MQTT broker, using the one public available	
	- Using now ws://iot.eclipse.org:80/ws for dashboards and tcp://iot.eclipse.org:1883 in PDI transformations.
	


