CREATE TABLE adventurers (
    id                  VARCHAR(64)     PRIMARY KEY,
    name                VARCHAR(100)    NOT NULL,
    adventurer_rank     VARCHAR(20)     NOT NULL,
    experience          INTEGER         NOT NULL
);

CREATE TABLE quests (
    id              VARCHAR(64)     PRIMARY KEY,
    title           VARCHAR(255)    NOT NULL,
    reward_gold     INTEGER         NOT NULL,
    required_rank   VARCHAR(20)     NOT NULL,
    status          VARCHAR(20)     NOT NULL,
    adventurer_id   VARCHAR(64),
    expires_at      TIMESTAMP WITH TIME ZONE
);
