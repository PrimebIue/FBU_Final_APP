Original App Design Project - README Template
===

# IceBreaker

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
IceBreaker is an app that allows you to connect with people that you are meeting for the first time. It allows you to input any hobbies, interests and personality traits that you like checks for coincidences with another person. 

The app allows you to log in with your Facebook account and generates a QR code to your profile which allows the people you meet to simply scan it and see what things you have in common.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Social
- **Mobile:** Mobile only experience, the app saves your profile settings and allows you to log in with your Facebook account. It uses the camera.
- **Story:** Allows users to break the ice with people they just met by checking if they have anything in common.
- **Market:** Any one who would like to make the process of meeting new people easier and fun.
- **Habit:** Users can use this app everytime they meet someone and want something to talk about.
- **Scope:** V1 would just allow people to see if they have any of their input attributes in common. And to add each other on Facebook. V2 would prompt the users with conversation ideas or questions. V3 (Maybe) Would allow people to chat through the app using Facebook's SDK

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* The user can log-in with their Facebook account
* The user can input their hobbies/interests into a list (Recycler View)
* User's inputs are saved between sesions.
* The app can generate a QR code that links to the User's profile that another user with the app can scan.
* After scanning the QR code the app checks for similarities between each User's hobbies/interests and displays the similarities
* The app allows you to add the other person as a friend in Facebook through the app.
* When looking at other user's hobbies/interests, the app allows you to double tap to add them to your list.

**Optional Nice-to-have Stories**

* After checking for similarities, the app gives you conversation prompts.
* The app allows you to access Facebook messenger. (Maybe)
* The app allows you to create private hobbies/interests that are only displayed if the other person has the same hobby/interest
* The app allows you to see a Timeline of your friends and see if anyone has added a new interest/hobby, if they recently added a friend, and Facebook posts from your friends related to any of your hobbies/interests.
* User can add additional information to their hobbies/interests.



### 2. Screen Archetypes

* Login Screen
   * User can login
* Registration Screen
   * Redirects the user to Facebook's registration
* Interests list
    * Allow the user to add interests/hobbies to a list
* QR code Screen
    * Generates a QR code with a link directly to the user's account and creates 

### 3. Navigation

**Tab Navigation** (Tab to Screen)


* Profile
* QR code functionality
* Hobby/Interest List
* Camera (For QR read)


**Flow Navigation** (Screen to Screen)

* Login Screen
   * Hobby/Interest List
* Hobby/Interest List
   * Hobby/Interest details (After clicking on an item on the list)
* QR code camera
    * Hobby/Interest pairing (After scanning someone else's QR code.) 

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/PrimebIue/FBU_Final_APP/blob/main/Wireframe_CryptoHub.jpeg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
