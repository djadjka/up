select users.name ,count(*)  as count From chat.messages inner join chat.users on users.id=messages.user_id where date >curdate() group by(users.id) having count > 1;