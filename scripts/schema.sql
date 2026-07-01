-- =====================================================
-- SQLite schema for todo-jdbc project
-- =====================================================

-- Table: person
CREATE TABLE IF NOT EXISTS person (
    person_id INTEGER PRIMARY KEY,
    first_name TEXT,
    last_name TEXT
);

-- Table: todo_item
CREATE TABLE IF NOT EXISTS todo_item (
    todo_id INTEGER PRIMARY KEY,
    title TEXT,
    description TEXT,
    deadline TEXT,        -- ISO-8601 date string
    done INTEGER DEFAULT 0,
    assignee_id INTEGER,
    FOREIGN KEY (assignee_id)
        REFERENCES person(person_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE INDEX IF NOT EXISTS idx_todo_item_assignee
    ON todo_item (assignee_id);

-- Table: todo_item_task
CREATE TABLE IF NOT EXISTS todo_item_task (
    task_id INTEGER PRIMARY KEY,
    title TEXT,
    description TEXT,
    done INTEGER DEFAULT 0,
    todo_item_id INTEGER,
    FOREIGN KEY (todo_item_id)
        REFERENCES todo_item(todo_id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

CREATE INDEX IF NOT EXISTS idx_task_todo_item
    ON todo_item_task (todo_item_id);
