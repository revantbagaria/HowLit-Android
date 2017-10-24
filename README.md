# HowLit-Android
Have you found yourself debating with your friends about which nightclub to go to on a weekend evening? HowLit can help you reach a consensus.

It is an android mobile application that queries the Yelp API to find a list of all the nightclubs in a specified city. The application allows users to "upvote" or "downvote" a venue for a given night, and keeps track of the votes in real time using Firebase (a realtime could-based database). Subsequently, other users can open the application and decide on where to go based on the votes.

In addition to using the Yelp api and Firebase, some other features we implemented in this project are: RecyclerView, CardView, and Toastmessages. Querying the Yelp api gives us access to a ton of data about the venues such as: location, prices, hours of operation, user ratings, reviews and so on. We have not utilized all of this data. Incorporating some of this data into the application can be a great way of extending it and building something more valuable. 

# Installation

This project is complete. Feel free to download it and run it. Please note, it needs to be run through Android Studio (which needs to be downloaded). 

# Note

Currently the Yelp api is queried for the city of San Franciso, the location (longitude and latitude) of which is hardcoded. This can easily be changed according to your requirement. 
