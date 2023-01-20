import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpServices, TagWithCount } from '../../http/http-service.service';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  tags: TagWithCount[] = [
    { id: 1, title: 'React' },
    { id: 2, title: 'Frontend' },
    { id: 3, title: 'Backend' },
    { id: 4, title: 'React Native' },
    { id: 5, title: 'Kotlin' },
    { id: 6, title: 'Spring Boot' },
    { id: 7, title: 'Angular' },
  ];
  tagsToSearchFor: Set<number> = new Set();
  constructor(
    private httpService: HttpServices,
    private cdRef: ChangeDetectorRef
  ) {
    httpService.fetchAllTagWithCount().subscribe((result) => {
      this.tags = result;
      cdRef.detectChanges();
    });
  }

  ngOnInit(): void {}
  addTag(tagId: number) {
    if (this.tagsToSearchFor.has(tagId)) this.tagsToSearchFor.delete(tagId);
    else this.tagsToSearchFor.add(tagId);
    console.log('searching For', this.tagsToSearchFor);
  }
}
