CREATE TABLE account (
    adventurer_id VARCHAR(64) PRIMARY KEY,
    balance INTEGER NOT NULL
);

CREATE TABLE ledger_entry (
    id VARCHAR(64) PRIMARY KEY,
    quest_id VARCHAR(64) NOT NULL,
    adventurer_id VARCHAR(64) NOT NULL,
    amount INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_ledger_entry_adventurer
    ON ledger_entry(adventurer_id);
