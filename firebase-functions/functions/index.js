const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
const profileDB = admin.firestore().collection('profiles');

exports.addUserToDB = functions.auth.user().onCreate((user) => {
  console.log("--Checkpoint--");
  const displayName = (user.displayName!==null ? user.displayName : '');
  console.log("Checkpoint name: " + displayName);
  const email = (user.email!==null ? user.email : '');
  console.log("Checkpoint email: " + email);
  const id = user.uid;
  console.log("Checkpoint id: " + id);
  const dp = (user.photoURL!==null ? user.photoURL : '');
  console.log("Checkpoint dp: " + dp);
  return profileDB.doc(email).set({
    email: email,
    id: id,
    name: displayName,
    dp: dp
  }).then(() => {
      console.log("Profile added: " + id);
      return null;
  }).catch(err => {
    console.error('Something is fucked up. ' + err)
  })
});