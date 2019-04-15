'use strict'


const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.sendNotification = functions.database.ref('/Notifications/{receiver_user_id}/{notification_id}')
.onWrite((data, context) =>
{
	const receiver_user_id = context.params.receiver_user_id;
	const notification_id = context.params.notification_id;


	console.log('We have a notification to send to :' , receiver_user_id);


	if (!data.after.val()) 
	{
		console.log('A notification has been deleted :' , notification_id);
		return null;
	}

	const DeviceToken = admin.database().ref(`/Users/${receiver_user_id}/deviceToken`).once('value');
	const Title = admin.database().ref(`/Notifications/${receiver_user_id}/${notification_id}/title`).once('value');
	const Message = admin.database().ref(`/Notifications/${receiver_user_id}/${notification_id}/message`).once('value');

	return DeviceToken.then(result => 
	{
		const token_id = result.val();
        console.log('TokenID: ', token_id);
        Title.then(result => {
        	const title = result.val().toString();
        	Message.then(res => {
        		const message = res.val().toString();
        		const payload = 
				{
					notification:
					{
						title: title,
						body: message,
						icon: "default"
					}
				
				};
				return admin.messaging().sendToDevice(token_id, payload)
					.then(response => 
						{
							console.log('This was a notification feature.');
						});
        	})
        })
		

		
	});
});


exports.sendFriendRequest = functions.database.ref('/FriendRequests/{receiver_user_id}/{friendRequest_id}')
.onWrite((data, context) =>
{
	const receiver_user_id = context.params.receiver_user_id;
	const friendRequest_id = context.params.friendRequest_id;


	console.log('We have a friend request to send to :' , receiver_user_id);


	if (!data.after.val()) 
	{
		console.log('A notification has been deleted :' , friendRequest_id);
		return null;
	}

	const DeviceToken = admin.database().ref(`/Users/${receiver_user_id}/deviceToken`).once('value');
	const Message = admin.database().ref(`/FriendRequests/${receiver_user_id}/${friendRequest_id}/message`).once('value');

	return DeviceToken.then(result => 
	{
		const token_id = result.val();
        console.log('TokenID: ', token_id);
    	Message.then(res => {
    		const message = res.val().toString();
    		const payload = 
			{
				notification:
				{
					title: "Friend Request",
					body: message,
					icon: "default"
				}
			
			};
			return admin.messaging().sendToDevice(token_id, payload)
				.then(response => 
					{
						console.log('Sent friend request.');
					});
        })
		

		
	});
});