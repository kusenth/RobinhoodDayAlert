# Robinhood Stock Alert


#### DISCLAIMER
I am not affiliated with Robinhood or its associates. This app relies on the private API as found here (special thanks to sanko, conrad weiser for providing documentation/API info). 


#### About

It is the spring boot application with scheduler which monitors the watchlist, portfolios and external stock sites and trigger alert to the Android mobile app based on the criteria.


#### Background

I have found lot of Robinhood supporting programs/apps which are providing Web UI and functionalities which are already provided by the Robinhood Web application and mobile app. So I have tried something different which are not available before. 

I thought of providing the Alert mechanism to the Android mobile phones for  each 

* Profit / Loss alert
* Time to sell criteria 
* Day losers alert 
* Watchlist price change alert
* Automate Limit buy/sell order based on customized criteria
* Automatically change the stop loss when the stock price rises
* Lot of features and still getting added...

If we have this program runs in the android platform then monitoring/network access, memory utilization may slow down your mobile phones, so i have decided to run all this complex program logic in the EC2 cloud platform and only push the notifications to the Android phone for each trigger. We are not sacrificing any load in our mobile phone with this approach.


#### Technical Aspects
Spring boot Application

Spring Scheduler for scheduling each activity based on Cron expression

Spring JPA and H2 database

Spring REST API

Amazon EC2 for the cloud deployment (Micro version - Free) 

Heroku - Optional for EC2

Google FCM for the Cloud messaging

Jsoup for the website crawling

It has extensive Java 8 features like Lambda and streams. Please install version 8 in the cloud machine.

#### Installation

Get the free EC2 account - Try for AMI or Red Hat image as Windows one may clog most of the 1 GB allocated RAM memory

Get the Google FCM API and token for the Android app - Search online for the article

Configure the application properties with the API token, Robin Hood Username and Password 

Make sure you change the time zone to the PST in the cloud machine

Build the jar and deploy into the EC2 console.

Install the Android app to receive the notification - Get it from my other repo
