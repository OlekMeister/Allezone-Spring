CREATE TABLE section(
    id INT NOT NULL,
    name VARCHAR NOT NULL unique,

    PRIMARY KEY (id)
);

CREATE TABLE category(
    id INT NOT NULL,
    name VARCHAR NOT NULL unique,
    section_id INT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT section_fk FOREIGN KEY (section_id) REFERENCES section (id)
);

CREATE TABLE auction(
    id INT NOT NULL,
    price DECIMAL NOT NULL,
    title VARCHAR NOT NULL,
    description text NOT NULL,
    creator_id INT NOT NULL,
    category_id INT NOT NULL,
    version INT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT user_fk FOREIGN KEY (creator_id) REFERENCES users (id)
);

CREATE TABLE auction_photo(
    id INT NOT NULL,
    auction_id INT NOT NULL,
    name VARCHAR NOT NULL,
    position INT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT auction_photo_fk FOREIGN KEY (auction_id) REFERENCES auction (id)
);

CREATE TABLE parameter(
    id INT NOT NULL,
    key VARCHAR NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE auction_parameter(
    auction_id INT NOT NULL,
    parameter_id INT NOT NULL,
    value VARCHAR NOT NULL,
    CONSTRAINT parameter_fk FOREIGN KEY (parameter_id) REFERENCES parameter (id),
    CONSTRAINT auction_fk FOREIGN KEY (auction_id) REFERENCES auction (id)
);

