import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import time
from datetime import datetime

# Use the application default credentials
cred = credentials.Certificate('/home/iiitd/Downloads/serviceAccount.json')
firebase_admin.initialize_app(cred)

db = firestore.client()

i = int(input("number of entries to be put in database"))
for x in range(i):
    docID = time.time()
    docID = str(docID)
    docID = docID + str(x)
    doc_ref = db.collection(u'requests').document(docID)
    doc_ref.set({
        u'date_generated':datetime.now(),
        u'id':docID,
        u'distance': 200,
        u'receiver_email': u'verynice@email.com',
        u'sender_email':str(docID) + str('@email.com'),
        u'sender_food': "good-Food",
        u'sender_name': str(docID),
        u'status':0,
    })  
print("done")
