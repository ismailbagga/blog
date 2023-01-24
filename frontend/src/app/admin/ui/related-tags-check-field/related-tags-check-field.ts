import { isNgTemplate, TemplateBindingParseResult } from '@angular/compiler';
import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import {
  ArticleHttpService,
  Tag,
} from 'src/app/core/global-services/http-article.service';

@Component({
  selector: 'app-related-tags-check-field',
  templateUrl: './related-tags-check-field.html',
  styleUrls: ['./related-tags-check-field.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RelaedTagCheckField implements OnInit, OnDestroy {
  foundTags: Tag[] = [];
  selectedResources: Tag[] = [];
  @Input('preloaded') preloadedResources: Tag[] = [];
  inputControler: FormControl = new FormControl('');
  @Input('addedResources') addedCtrl!: FormControl;
  @Input('removedResources') removedCtrl!: FormControl;
  @Input('foundTagss') set setfoundTagss(arr: any[]) {
    const selectedIdArray = this.selectedResources.map((item) => item.id);
    this.foundTags = arr.filter((item) => !selectedIdArray.includes(item.id));
  }
  subscription: Subscription | undefined;
  term = '';
  constructor(private articleService: ArticleHttpService) {}
  ngOnDestroy(): void {
    this.subscription && this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.selectedResources = [...this.preloadedResources];
    this.subscription = this.inputControler.valueChanges.subscribe((value) =>
      this.findByTerm(value)
    );
    console.log('preloaded init:', this.preloadedResources);
    console.log('selected resources', this.selectedResources);
  }
  findByTerm(term: string) {
    console.log('searching for');
    if (term.trim() === '') return;
    this.articleService.searchforTagsByTitle(term).subscribe((response) => {
      this.foundTags = response;
      // this.changeDetectionRef.detectChanges();
    });
  }

  addResource(id: number) {
    console.log('adding 1 ');

    const resourse = this.foundTags.find((item) => item.id === id);
    if (resourse) {
      this.foundTags = this.foundTags.filter((res) => res.id !== id);
      this.selectedResources.push(resourse);
      this.patchValues(resourse, 'ADD');
    }
    // resourse && this.selectedResources.push(resourse);
  }
  removeResource(id: number) {
    const resourse = this.selectedResources.find((item) => item.id === id);
    if (resourse) {
      this.selectedResources = this.selectedResources.filter(
        (res) => res.id !== id
      );
      if (resourse.title.toLowerCase().includes(this.term.toLowerCase())) {
        this.foundTags.push(resourse);
      }
      this.patchValues(resourse, 'REMOVE');
    }
  }
  patchValues(resouce: Tag, action: 'REMOVE' | 'ADD') {
    //  Convert  topics to list of id
    const preloadedIds = this.preloadedResources.map((item) => item.id);
    // if resource id is included in already add resources
    const isIncluded = preloadedIds.includes(resouce.id);
    console.log('preloaded before :', this.preloadedResources);

    if (action === 'REMOVE') {
      // if i want to remove resource already included
      // i sould mark to be deleted to server
      if (isIncluded)
        this.removedCtrl.patchValue([...this.removedCtrl.value, resouce.id]);
      // if i want to remove resource i just add
      // only remove it from added ctrl
      else
        this.addedCtrl.patchValue(
          this.addedCtrl.value.filter((item: number) => item != resouce.id)
        );
    }
    if (action === 'ADD') {
      if (!isIncluded) {
        this.addedCtrl.patchValue([...this.addedCtrl.value, resouce.id]);
      } else
        this.removedCtrl.patchValue(
          this.removedCtrl.value.filter((item: number) => item != resouce.id)
        );
    }
    console.log('after patching');
    console.log('preloaded after ', this.preloadedResources);
    console.log('added resources', this.addedCtrl.value);
    console.log('removed resources', this.removedCtrl?.value);
  }
}
