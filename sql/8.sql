select users.name,messages.date,messages.text from chat.messages inner join chat.users on users.id=messages.user_id  order by (date)