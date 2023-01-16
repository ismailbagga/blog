CREATE OR REPLACE FUNCTION on_article_delete_handler() RETURNS TRIGGER LANGUAGE plpgsql
as $$
DECLARE

BEGIN
    -- 		 RAISE WARNING 'DELETE TRIGGREED .....' ;
    if ( ( OLD.prev_article_id IS NOT NULL )  AND   (OLD.next_article_id IS NOT NULL)  ) THEN
-- 		  RAISE WARNING 'Both Articles Are  There' ;
        UPDATE article
        SET next_article_id =  ( CASE WHEN id = OLD.prev_article_id THEN OLD.next_article_id  ELSE next_article_id  END) ,
            prev_article_id =  ( CASE WHEN id = OLD.next_article_id THEN OLD.prev_article_id  ELSE prev_article_id  END)
        WHERE id = OLD.prev_article_id OR id = OLD.next_article_id  ;
        RETURN OLD ;


    ELSEIF OLD.prev_article_id IS NOT NULL  THEN
-- 		 RAISE WARNING 'Prev Article Is There' ;

        UPDATE article
        SET next_article_id = NULL
        WHERE id = OLD.prev_article_id  ;
        RETURN OLD ;


    ELSEIF OLD.next_article_id IS NOT NULL  THEN
-- 		 RAISE WARNING 'Next Article Is There' ;
        UPDATE article
        SET prev_article_id = NULL
        WHERE id = OLD.next_article_id  ;
        RETURN OLD ;

    ELSE
-- 			RAISE WARNING 'ISOLATED Article Nothing to change' ;
        RETURN OLD ;
    END IF ;
END

$$ ;

DROP TRIGGER IF EXISTS article_delete_trigger ON public.article;
CREATE TRIGGER article_delete_trigger BEFORE delete ON public.article
    FOR  EACH ROW EXECUTE PROCEDURE on_article_delete_handler()

