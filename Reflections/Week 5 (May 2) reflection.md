## What have you accomplished this week? (Please list each accomplishment, and explain)
Rebuilt the login/register account screens again to have functionality. 
ADDED FIREBASE!:
Users are now able to login, logout, create accounts and it is reflective on the firebase console :) 
Users can go back and forth between login and register pages. 
Users stay logged in until they click logout
Sources I've used so far this week:
https://youtu.be/tbh9YaWPKKs
https://youtu.be/TwHmrZxiPA8

## What challenges or difficulties did you face? If you solved them, how? If not, what have you learned so far? Have you sought help or other resources?
I went to lab Sunday to get help from Imma. We tried to get the android phone to connect to Android studio (for like an hour), and we did not master it. However, my emulator works perfectly now (knock on wood) and has only crashed once so far (as of Saturday, May 1st.) 
Update Sunday, my code ran this error "Cannot fit requested classes in a single dex file (# methods: 96827 > 65536)" I am currently trying to fix it by doing what Stack Overflow told me to do, which is add "implementation 'com.android.support:multidex:1.0.3'" in dependencies section of Module level build.gradle file, and "multiDexEnabled true" in the default config section of Module level build.gradle file. -> This issue was fixed.
I attempted to add the task info to the firestore, but seemingly that broke android studio. I am going to try to go to lab tonight (Thursday) to see what I can get help with. 

## What do you plan to accomplish in the following week? (Please list at least 3 concrete "S.M.A.R.T." goals, along with estimated number of hours to complete, or day to complete)
Well, ideally I want to have at least some functionality to my app. Set tasks and see those on the todo screen, save them in the firestore database. Dr. Jones suggested that I create the task document and collection in firestore directly, then see if I can retrieve it on android studio.

## What resources will you use to accomplish your goals for the upcoming week? (please list out the resources)
The firebase documents I've been using for a while now. I'll link any videos or other resources I use. 