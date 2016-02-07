CREATE TABLE IF NOT EXISTS users
 (username TEXT PRIMARY KEY,
  gcm_id TEXT UNIQUE,
  password TEXT,
  e_mail TEXT,
  phone TEXT);

CREATE TABLE IF NOT EXISTS messages
 (sender_id TEXT REFERENCES users (gcm_id),
  receiver_id TEXT REFERENCES users (gcm_id),
  body TEXT,
  location POINT,
  sendt_time TIMESTAMP,
  PRIMARY KEY (sender_id, receiver_id));
