MinimalGoogleCalendarWebAccess
==============================

This is a minimal maven project which connects to Google Calendar version 1.15.0-rc using OAuth 2.0.

The first time you run this program, you will be prompted for your own application's Google authentication credentials.
You can create a new application and get credentials here: https://code.google.com/apis/console
This will create a private "config.xml" file that you can edit later at any time if your credentials change.
This project is for a Web Server application, choose your client id and secret accordingly.
You may choose a redirect URI such as "http://localhost:8080/callback/" for testing purposes.

To build the project requires Maven and Java 1.6 or later:

    mvn clean install

To run the example, you can then use:

    mvn exec:java -Dexec.mainClass="glank.app.examples.google.calendar.SimpleCalendarTest"

The project comes with an Eclipse build configuration that will perform both of these commands for you if you are using eclipse.

You will be given a URL to go to in a browser. Once there, you will be prompted to allow access to your calendar data.
If you click yes, you will be redirected to the URI you specified in GCGlobals.
In the address bar, after the "?" in the URL, should be a parameter like this "code=vs834jn8us.l29jr/2l3kj5j2"
Copy that value, "vs834jn8us.l29jr/2l3kj5j2", and paste it when the program asks for the code.
If all your credential's check out, you should be provided with a list of all your Google Calendars.

That is the OAuth 2.0 authentication process in the simplest example I could muster.
See the source code for further explanation, it is thoroughly commented.
