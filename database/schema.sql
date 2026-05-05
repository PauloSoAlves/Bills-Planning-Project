CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  email varchar UNIQUE NOT NULL,
  password varchar NOT NULL,
  username varchar NOT NULL
);

CREATE TABLE member (
  member_id SERIAL PRIMARY KEY,
  name varchar NOT NULL,
  user_id integer NOT NULL REFERENCES users(user_id)
);

CREATE TABLE credit_card (
  card_id SERIAL PRIMARY KEY,
  alias varchar NOT NULL,
  final_numbers integer NOT NULL,
  due_day integer NOT NULL,
  user_id integer NOT NULL REFERENCES users(user_id)
);

CREATE TABLE category (
  category_id SERIAL PRIMARY KEY,
  name varchar NOT NULL,
  user_id integer REFERENCES users(user_id));

CREATE TABLE income (
  income_id SERIAL PRIMARY KEY,
  amount decimal(12,2) NOT NULL,
  income_date date NOT NULL,
  occurrences integer,
  member_id integer NOT NULL REFERENCES member(member_id),
  category_id integer NOT NULL REFERENCES category(category_id)
);

CREATE TABLE expense (
  expense_id SERIAL PRIMARY KEY,
  amount decimal(12,2) NOT NULL,
  expense_date date NOT NULL,
  occurrences integer,
  is_paid boolean DEFAULT false,
  priority integer,
  member_id integer NOT NULL REFERENCES member(member_id),
  category_id integer NOT NULL REFERENCES category(category_id),
  card_id integer REFERENCES credit_card(card_id)
);
