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

* [ ] User can enter hobby editing mode by long clicking a Hobby
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
<img src="https://github.com/PrimebIue/FBU_Final_APP/blob/main/IceBreaker_Wireframe.jpeg" width=600>

### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/PrimebIue/FBU_Final_APP/blob/main/Digital_Wireframe.png" width=800>

### [BONUS] Interactive Prototype

<img src='https://github.com/PrimebIue/FBU_Final_APP/blob/main/Interactive_Prototype.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

## Schema 

### Models

User
| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user |
   | username      | String   | User's username  |
   | password      | String   | User's password  |
   | email         | String   | User's email     |
   | bio           | String   | User's Bio       |
   | Profile Picture | File   | User's profile picture
   | authData      | Object   | Contains the user's authentication data if they logged in with Facebook |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   
Hobby

| Property   | Type    | Description |
| ------------- | -------- | ------------|
| objectId      | String   | unique id for the hobby |
| createdAt     | DateTime | date when post is created (default field) |
| updatedAt     | DateTime | date when post is last updated (default field) |
| name          | String   | Hobbby's name |
| verified      | Boolean  | Determines if the hobby was revised by the developers |
| hobbyDescription | String | Basic description of the hobby |
| hobbyImage    | File     | Image of the hobby |
| hobbyTags     | Array    | Contains basic descriptive tags of the hobby for pairing |


### Networking

List of network requests by screen
- Login Screen
  - (LOGIN) Login user with parse
    ```swift
    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user with email: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, R.string.login_issue, Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
            }
        });
    }
    ```
  - (LOGIN) Login user with Facebook
    ```swift
    btnLoginFB.setOnClickListener(v -> {
         final ProgressDialog dialog = new ProgressDialog(this);
         dialog.setTitle(getString(R.string.wait_moment));
         dialog.setMessage(getString(R.string.login_in));
         dialog.show();
         Collection<String> permissions = Arrays.asList("public_profile", "email");
         ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, (user, err) -> {
             dialog.dismiss();
             if (err != null) {
                 Log.e(TAG, "done: ", err);
                 Toast.makeText(this, err.getMessage(), Toast.LENGTH_LONG).show();
             } else if (user == null) {
                 Toast.makeText(this, R.string.cancel_fb_login, Toast.LENGTH_LONG).show();
                 Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
             } else if (user.isNew()) {
                 Toast.makeText(this, R.string.sign_login_facebook, Toast.LENGTH_LONG).show();
                 Log.d(TAG, "User signed up and logged in through Facebook!");
                 getUserDetailFromFB();
             } else {
                 Toast.makeText(this, R.string.login_facebook, Toast.LENGTH_LONG).show();
                 Log.d(TAG, "User logged in through Facebook!");
                 showAlert(getString(R.string.oh_you), getString(R.string.welcome_back));
             }
         });
     });
     ```
- Sign Up screen
  - (Create/USER) Creates new parse user
    ```swift
    // Create Parse user
    ParseUser user = new ParseUser();

    // Get strings from edit text
    String username = etUsername.getText().toString();
    String password = etPassword.getText().toString();
    String email = etEmail.getText().toString();
    // Set core properties
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    // invoke signup
    user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if (e != null) {
                Log.e(TAG, "Issue with SignUp", e);
                Toast.makeText(SignUpActivity.this, R.string.sign_up_issue, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(SignUpActivity.this, R.string.successful_sign_up, Toast.LENGTH_SHORT).show();
            finish();
        }
    });
    ```
    
- Profile screen
  - (Read/GET) Query user information
      ```swift
      ParseUser user = ParseUser.getCurrentUser();

      tvUsername.setText(user.getUsername());
      tvBio.setText(user.getString("bio"));

      ParseQuery<Hobby> query = ParseQuery.getQuery(Hobby.class);

      try {
          tvHobbiesNumber.setText(String.valueOf(query.whereEqualTo("usersWithHobby", user).count()));
      } catch (ParseException e) {
          e.printStackTrace();
      }

      Glide.with(getContext())
              .load(user.getParseFile("profilePicture").getUrl())
              .into(ivProfilePicture);
      ```
- Edit Profile Screen

  - (Read/GET) Query current user information
    ```swift
    ParseUser user = ParseUser.getCurrentUser();

    Glide.with(this)
            .load(user.getParseFile("profilePicture").getUrl())
            .into(ivProfilePicture);

    etUsername.setText(user.getUsername());
    etDescription.setText(user.getString("bio"));
    ```
  - (Update/PUT) Update user profile
    ```swift
    ParseUser user = ParseUser.getCurrentUser();
       
    user.setUsername(username);
    user.put("bio", description);

    user.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e != null) {
                Log.e(TAG, "Error while saving", e);
                Toast.makeText(getContext(), R.string.saving_error, Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, getString(R.string.user_save_successful));
            getDialog().dismiss();
        }
    });
     ```
- Hobbies list screen
  - (Read/GET) Query user's hobbies
    ```swift
    private void queryHobbies() {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }
                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "Hobby: " + hobby.getName());
                }
                Log.i(TAG, "Gets here" + hobbies);
                allHobbies.addAll(hobbies);
                adapter.notifyDataSetChanged();
            }
        });
    }
     ```
  - (Read/GET) Query an update for user's hobbies
     ```swift
     private void queryHobbiesUpdate() {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }

                allHobbies.clear();
                adapter.notifyDataSetChanged();

                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "Hobby: " + hobby.getName());
                }
                Log.i(TAG, "Gets here" + hobbies);
                allHobbies.addAll(hobbies);
                adapter.notifyDataSetChanged();
            }
        });
    }
    ```
- Add Hobbies screen
  - (Read/GET) Get all hobbies in database
    ```swift
    private void queryHobbies() {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }

                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "Hobby: " + hobby.getName());
                }
                Log.i(TAG, "Gets here" + hobbies);
                allHobbies.addAll(hobbies);
                adapter.notifyDataSetChanged();
            }
        });
    }
    ```
  - (Read/GET) Query an update for hobbies
     ```swift
     private void queryHobbiesUpdate() {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }

                allHobbies.clear();
                adapter.notifyDataSetChanged();

                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "Hobby: " + hobby.getName());
                }
                Log.i(TAG, "Gets here" + hobbies);
                allHobbies.addAll(hobbies);
                adapter.notifyDataSetChanged();
            }
        });
    }
    ```
- Create hobby screen
  - (Create/HOBBY) Create new hobby in Parse
    ```swift
    private void saveNewHobby() {

          // Set hobby name
          Hobby hobby = new Hobby();
          hobby.setDescription(hobbyDescription);
          hobby.setName(etHobbyName.getText().toString());
          hobby.setTags(newHobbyTags);

          if (image != null)
              hobby.setImage(image);

          if (newHobbyTags.size() != 0) {
              hobby.saveInBackground(new SaveCallback() {
                  @Override
                  public void done(ParseException e) {
                      if (e != null) {
                          Log.e(TAG, "Error while saving hobby.", e);
                          Toast.makeText(getContext(), R.string.saving_error, Toast.LENGTH_SHORT).show();
                      }
                      Log.i(TAG, "Hobby save successful");
                      getDialog().dismiss();
                  }
              });
          } else {
              Toast.makeText(getContext(), "Sorry, add at least 1 tag.", Toast.LENGTH_SHORT).show();
              getDialog().dismiss();
          }
      }
      ```
- User hobby pairing screen
  - (Read/GET) Query current user hobbies
    ```swift
    private void queryCurrUserHobbies() {
      // Specify data to query
      ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
      query.include("usersWithHobby");
      query.whereEqualTo("usersWithHobby", user);
      query.findInBackground(new FindCallback<Hobby>() {
          @Override
          public void done(List<Hobby> hobbies, ParseException e) {
              // Check for errors
              if (e != null) {
                  Log.e(TAG, "Issue with getting hobbies.", e);
              }

              for (Hobby hobby : qrHobbies) {
                  Log.i(TAG, "qrHobbies" + hobby.getName());
              }
              for (Hobby hobby : hobbies) {
                  Log.i(TAG, "allHobbies" + hobby.getName());
                  if (!qrHobbies.contains(hobby))
                      allHobbies.add(hobby);
              }

              adapter.notifyDataSetChanged();
          }
      });
    }
    ```
  - (Read/GET) Query qr user's hobby
    ```swift
    private void queryQRHobbies() {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }

                qrHobbies.addAll(hobbies);

                tvHobbiesNumber.setText(String.valueOf(qrHobbies.size()));
                queryCurrUserHobbies();
            }
        });
    }
    ```
 
    
#### [OPTIONAL:] Existing API Endpoints
##### QR Code API
- Base URL - [https://api.qrserver.com/v1/create-qr-code/](https://api.qrserver.com/v1/create-qr-code/)

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /?size=heightxwidth&data=String | define the size of the QR Code and create one with the specified string
    
##### Google Custom Search API
- Base Url - [https://www.googleapis.com/customsearch/]()
- 
  HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /v1?q=String | Search the String in the custom engine
    `GET`    | &key=String   | API Key
    `GET`    | &cx=String      | Custom Engine ID
    `GET`    | &alt=FileType  | Requested type of File for response
    `GET`    | &searchType=Image | Requested to search only for image results
    
