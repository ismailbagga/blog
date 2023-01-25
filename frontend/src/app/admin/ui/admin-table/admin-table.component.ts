import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { ArticlePreview } from 'src/app/core/global-services/http-article.service';
export type AdminElement = {
  id: number;
  title: string;
  slug: string;
};
@Component({
  selector: 'app-admin-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['./admin-table.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminTableComponent implements OnInit {
  @Input() elements: AdminElement[] = [];
  @Output() onDelete = new EventEmitter<number>();
  @Output() onDetails = new EventEmitter<string>();
  @Output() onEdit = new EventEmitter<string>();
  selectedArticleId: number | null = null;
  isModelHidden = true;
  constructor() {}

  ngOnInit(): void {}
  showModel(articleId: number) {
    this.selectedArticleId = articleId;
    this.isModelHidden = false;
  }
}
