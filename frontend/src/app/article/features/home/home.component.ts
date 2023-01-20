import { ArticlePreview } from './../../http/http-service.service';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpServices, TagWithCount } from '../../http/http-service.service';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  tags: TagWithCount[] = [];
  latestTag: TagWithCount = {
    id: -1,
    title: 'Latest',
    slug: 'latest',
    count: 0,
  };
  articles: ArticlePreview[] = [];
  filteredArticles: ArticlePreview[] = [];
  tagsToSearchFor: Set<number> = new Set([-1]);
  constructor(
    private httpService: HttpServices,
    private cdRef: ChangeDetectorRef
  ) {
    httpService.fetchAllTagWithCount().subscribe((result) => {
      this.tags = result;
      cdRef.detectChanges();
    });
    httpService.fetchLatestArticles().subscribe((result) => {
      this.articles = result;
      this.filteredArticles = this.articles;
      this.latestTag = { ...this.latestTag, count: result.length };
      cdRef.detectChanges();
    });
  }

  ngOnInit(): void {}
  addTag(tagId: number) {
    if (this.tagsToSearchFor.has(tagId) && tagId !== -1) {
      this.tagsToSearchFor.delete(tagId);
    } else {
      tagId === -1 && this.tagsToSearchFor.clear();
      this.tagsToSearchFor.has(-1) && this.tagsToSearchFor.delete(-1);
      this.tagsToSearchFor.add(tagId);
    }

    this.filterArticles();
  }
  filterArticles() {
    const hasLatestTag = this.tagsToSearchFor.has(-1);
    if (hasLatestTag) {
      this.filteredArticles = [...this.articles];
      return;
    }
    this.filteredArticles = this.articles.filter((article) => {
      for (const tag of article.relatedTags) {
        if (this.tagsToSearchFor.has(tag.id)) return true;
      }
      return false;
    });
  }
}
