INSERT IGNORE INTO accounts (id,owner,balance) VALUES ('0','Brian Heaphy',10000);
INSERT IGNORE INTO accounts (id,owner,balance) VALUES ('1','Dummy Account',0);

INSERT IGNORE INTO transactions (id,sender_id,recipient_id,amount) VALUES ('0','0','1',500);