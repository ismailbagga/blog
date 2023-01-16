CREATE FUNCTION article_insert_trigger()
	RETURNS TRIGGER
	LANGUAGE plpgsql
	as $$
declare
found_prev_article_id bigint ;
BEGIN
            found_prev_article_id := (
				SELECT id FROM article
					WHERE  ( created_at <=  new.created_at and id <> NEW.id )
					ORDER BY created_at DESC  , id
                    LIMIT 1
                ) ;
			IF found_prev_article_id IS  NULL THEN
				RETURN NEW ;
            ELSE

                UPDATE article
                SET next_article_id = (CASE
                                        WHEN (id = found_prev_article_id) THEN new.id
                                        ELSE next_article_id
                    END),
                    prev_article_id = (CASE
                                        WHEN id = new.id THEN fodun_prev_article_id
                                        ELSE prev_article_id END)
                WHERE id = prev_article_id
                   OR id = new.id;
                return NEW;
            END IF ;

END
		$$ ;

CREATE TRIGGER after_insert_article
    AFTER INSERT
    ON article
    for each row
    execute procedure article_insert_trigger()