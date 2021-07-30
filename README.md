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

- **Category:** Social
- **Mobile:** Mobile only experience, the app saves your profile settings and allows you to log in with your Facebook account. It uses the camera.
- **Story:** Allows users to break the ice with people they just met by checking if they have anything in common.
- **Market:** Any one who would like to make the process of meeting new people easier and fun.
- **Habit:** Users can use this app everytime they meet someone and want something to talk about.
- **Scope:** V1 would just allow people to see if they have any of their input attributes in common. And to add each other on Facebook. V2 would prompt the users with conversation ideas or questions. V3 (Maybe) Would allow people to chat through the app using Facebook's SDK

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**


* [x] The user can log-in with their Facebook account
* [x] The user can navigate the app using Bottom navigation.
* [x] User's inputs are saved between sesions.
* [x] The app can generate a QR code that links to the User's profile that another user with the app can scan.
* [x] The user can input their hobbies/interests into a list (Recycler View).
* [x] User can select hobbies by selecting them from a list or adding a new one if it is not yet created.
* [x] User can when creating new hobbies select up to 4 tags that represent that hobby
* [x] User can look at a list containing their hobbies and each hobby displays its tags using a custom library for visual polish.
* [x] The app automatically adds an image and basic description to a newly created hobby using Custom Google Search API
* [x] After scanning the QR code the app checks for similarities between each User's hobbies/interests and displays the identical hobbies
* [ ] User can zoom in into the qr code by using 2 finger gesture


**Optional Nice-to-have Stories**

* [ ] The app allows you to add the other person as a friend in Facebook through the app.
* [ ] After checking for similarities, the app gives you conversation prompts.
* [ ] App allows user to add a playlist from Spotify.
* [ ] The app lets you see if someone scanned you code and allows to look at their profile.
* [ ] Hobby pairing also pairs hobbies with the same tags and adds them to a grid view.
* [ ] User can have a list of friends.


### 2. Screen Archetypes

* Login Screen
   * User can login
* Registration Screen
   * Redirects the user to Facebook's registration
* Interests list
    * User can look at a list of their current hobbies, with pictures and basic descriptions
    * Allow the user to enter the add hobbies screen
* Add Hobby screen
    * Shows a list of hobbies which the user can pick from
    * User can decide to enter the create hobby screen
* Create hobby screen
    * User can select a name for their new hobby
    * User can select up to 4 tags that properly represent the hobby created and at least 1
* QR code Screen
    * Generates a QR code with a link directly to the user's account and creates 
* Profile Screen
    * User can modify their description
    * User can change their profile picture
    * User can Logout

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Profile
* QR code functionality
* Hobby/Interest List

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Hobby/Interest List
* Hobby/Interest List
   * Add new hobby screen
     * Create new hobby screen 
* QR code screen
    * Qr code scanner
      * Profile of person you're pairing with
         * Hobby pairing 

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/PrimebIue/FBU_Final_APP/blob/main/CryptoHub_Wirefram.jpeg" width=600>

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
