# About #

Pondskum provides a simple API that allows you to log into your Bigpond account and retrieve your Internet usage data (IUD). Why not use the website? I wanted something simple to give me my IUD at a glance by clicking one button.

Bigpond does not expose a webservice with this information (to my knowledge). The API does the painful job of parsing a couple of web pages and returning only the IUD. As such this solution is quite brittle. If the website changes this API will stop working.

_Whether I update this API when the bigpond website changes is dependent on a few things such as time as well as contributors._

In the mean time enjoy the API.

# Requirements #

  * JDK/JRE 1.6 or greater
  * Linux/Windows
  * A Bigpond Internet account

# Screenshots #

Here's a sample screenshot of the type of output you can expect:

## The Tablet GUI ##

![http://pondskum.googlecode.com/svn/wiki/images/pondskum-1.png](http://pondskum.googlecode.com/svn/wiki/images/pondskum-1.png)

## Progression GUI ##

![http://pondskum.googlecode.com/svn/wiki/images/pondskum-2.png](http://pondskum.googlecode.com/svn/wiki/images/pondskum-2.png)

_Obviously since you have an API you can create any type of interface you like in front of it._

# Configuration #

All that needs to be configured is the _bigpond\_config.properties_ file with your user details. A sample configuration file is found in the **etc** directory, named _bigpond\_config.sample_. Simply rename this file (or make a copy of it to) _bigpond\_config.properties_. Add your **username** and **password** and uncomment **#log** entry to enable logging. Once you have done that head over to the **bin** directory. Depending on your OS, you can run any executable whose name starts with "run".

Eg. on linux you could run `./runSimpleCMD.sh`

There are currently a few front-ends to the API.

  1. A simple commandline (runSimpleCMD)
  1. A dialog with a progress bar (runProgression)
  1. A table with the usage information (runTablet)
  1. An MVC GUI with dialog and trayicon (runMVC)

# Problems #

Have a look at the _logs/ps.log_ file. See if there are any obvious errors.

Possible causes could be:

  1. A firewall blocking the program.
  1. Insufficient privileges to write the log and temp files.

# API #

The API can be used as such:

```
BigpondUsageInformation usageInformation = new BigpondConnectorImpl(loadProperties()).connect();
```

Where `loadProperties()` simply returns a `java.util.Properties` object with the user information populated. To load a named configuration file as a system property you could use:

```
new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("your_sys_property_name");
```

The names of the properties required can be found in the `bigpond_config.properties` file.

If you are happy with the defaults you can use:
```
ClientBuilder builder = new ClientBuilder();
BigpondUsageInformation usageInformation = new BigpondConnectorImpl(builder.getConfig()).connect();
```

For further examples on usage see the `SimpleCMD` and `TabletRunner` classes.

Connecting to Bigpond to test the API can be cumbersome. I've created 2 builder classes `StubbyBigpondUsageInformationBuilder` and `StubbyNewMonthBigpondUsageInformationBuilder` to build 2 stub versions of the API which returns canned data.They each return a `BigpondUsageInformation` implement when the `build()` method is called.

The API returns the following information:

```
public interface BigpondUsageInformation {

    BigpondAccountInformation getAccountInformation();

    List<BigpondMonthlyUsage> getMonthlyUsageList();

    BigpondUsage getTotalUsage();
}
```

```
public interface BigpondAccountInformation {

    String getAccountName();

    String getAccountNumber();

    String getCurrentPlan();

    String getMonthlyAllowance();

    String getMonthlyPlanFee();
}
```

```
public interface BigpondMonthlyUsage {

    String getMonth();

    BigpondUsage getBigpondUsage();
}
```

```
public interface BigpondUsage {

    String getDownloadUsage();

    String getUploadUsage();

    String getTotalUsage();

    String getUnmeteredUsage();
}

```

# Code #

The is provided "as is" as per the Apache V2 license.

If you would like to join the project or have some feedback drop me a line.