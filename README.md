# energyanalyzer

A Clojure library designed to analyze energy usage data from the National Renewable Energy Laboratory.
This is an open source project for the Den of Clojure and we encourage participation.  Roll up your sleeves, hop in, and code!

## Usage

lein clean
lein run

## The problem

The test problem I suggest is start with files containing 1 years' worth of 15-minute energy usage data and do some calculations within and between files.  This is the sort of data you would get from a smart meter on your house.  There are at least 64 apps available that consume this kind of data to provide visualizations, energy savings tips, and social games (http://en.openei.org/apps/?keyword=Green%20Button%20Apps).  Several of the more sophisticated apps are doing big-data type analysis on the backend to recognize patterns associated with specific problems like a broken refrigerators, furnaces that need a filter change, etc.
The sample calculations I'd suggest (and will try to hack some Clojure solution for) are:
1.       Summarize the data into hourly, daily, weekly, and monthly time-series.

2.       Calculate the min, max, average, and standard deviation for the daily, weekly, and monthly summaries.

3.       Add all the time-series together to create a utility demand curve.

4.       Sort 3 from highest to lowest to show what is called a load-duration curve.  See below for why this is important to utility customers.

5.       Find the days that contain the 350 highest 15-minute intervals in the load-duration curve.

Each file will contain the data for 1 building, either a home or a commercial office.  The column definitions:
1.       IntervalTimeStamp the time the measurement period ends.  The last interval of the day has the timestamp of the next day 00:00.  I don't know why they don't make it the 24:00 for the day being measured, but that's how the utility industry does it.  When summing values the summed value's timestamp is the timestamp of the last value in the summation.  E.G. The timestamp for the first hour of the year is 1/1/2012 01:00
2.       kWh the energy consumed over the previous 15 minutes.  These values can be summed directly to get the summary value.  E.G Add the 4 values 00:15, 00:30, 00:45, 01:00 together to get the consumption for the first hour of the year.
3.       qualityOfReading  - List of codes indicating the quality of the reading.  When summing intervals take the max qualityOfReading as the qualityOfReading for the summed value.  The meaning are defined using the specification:

0 - valid: data that has gone through all required validation checks and either passed them all or has been verified
7 - manually edited: Replaced or approved by a human
8 - estimated using reference day: data value was replaced by a machine computed value based on analysis of historical data using the same type of measurement.
9 - estimated using linear interpolation: data value was computed using linear interpolation based on the readings before and after it
10 - questionable: data that has failed one or more checks
11 - derived: data that has been calculated (using logic or mathematical operations), not necessarily measured directly
12 - projected (forecast): data that has been calculated as a projection or forecast of future readings
13 - mixed: indicates that the quality of this reading has mixed characteristics
14 - raw: data that has not gone through the validation, editing and estimation process
15 - normalized for weather: the values have been adjusted to account for weather, in order to compare usage in different climates
16 - other: specifies that a characteristic applies other than those defined
17 - validated: data that has been validated and possibly edited and/or estimated in accordance with approved procedures
18 - verified: data that failed at least one of the required validation checks but was determined to represent actual usage

I've provided a couple of sample data files that will work well for unit testing and one real data file.  I'll put together a couple thousand more real data files and send you a link to NREL's sftp site.  The real data will be the result of detail simulation tools or fuzzed actual data.  In any case they will look very similar to what actual building would consume.  I can't provide actual data because of privacy concerns.
README.rtf this file.
Test15Minute.csv a year and a week of fake 15-minute data.  The values are defined so that the hourly sums will be 1-8784 (2012 is a leap year).
TestHourly.csv a year and a week of hourly summations of the Test15Minute data.
TestDaily.csv a year and a week of daily summations of the Test15Minute data with summarization values in DayMin, DayMax, and DayAverage columns.
GoldenOffice.csv a year of 15-minute simulated energy consumption data.  The values are from a detailed simulation tool called EnergyPlus for a typical 1500 sqft office building located in Golden.

## License

Copyright © 2012 Den of Clojure http://www.meetup.com/Denver-Clojure-Meetup/

Distributed under the Eclipse Public License, the same as Clojure.
