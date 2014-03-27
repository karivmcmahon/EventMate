Event-Mate
=========

Event-Mate is website created in Java with a Cassandra database.
You sign up to Event-Mate, give information like gender preference for attending events and age (max, min) for attending events with as well as a travel distance preference.
Once logged on, EventMate displays events within your distance preference based on your postcode to the events postcode using kilometers.
Once you click attend, Event-Mate will add 3 friends who aren't currently your friends and want to go to the same event. The addition of friends is based on people who have the most in common with you and your preferences set in sign up or in settings. Once friends, you are then able to message each other and organise going to events. 

Below is an explanation of the JSP pages in Event-Mate

Header.jsp
  - contains the header thats used through-out the website and contains links that go to each page.

Home.jsp
  - this is the sign-up page which is shown first and can allow users to login to the website or sign-up.

Homepage.jsp
  - once logged in, this is the main page which shows all popular (attendee amount - > 2000)  events based on the logged in users preferences.

BioInfo.jsp
  - this page is shown when the user is signing up so that they can set their age, bio and preferences.
  
Event.jsp
  - shows more details of the partiular event thats been clicked on.

Friends.jsp
  - displays all the users friends when you've clicked attending to an event that other users are going to.

Interests.jsp
  - when signing up, the user is directed to this page where he/she can add in their interests through hobbies/sports and music.

Message.jsp
  - this is the jsp that shows an individual message to another member. 

Messages.jsp
  - this shows the list of conversations that the user is having with other members.

PageNotFound.jsp
  - error page which is shown when a page isn't found.

Profile.jsp
  - this displays the current users profile. It contains their set bio, age, gender and interests. 

Random.jsp
  - this displays an event which is in the users set distance range and they aren't currently attending.

Search.jsp
  - displays all results from the search bar on the page.

Searchbar.jsp
  - stores the search bar which is included in all pages.

SettingInterests.jsp
  - this page allows user to edit their current interest preferences.

Settings.jsp
   - in this page, the user can change their account information like age, bio and relationship status.
