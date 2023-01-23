import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { AuthService } from 'src/app/core/global-services/auth.service';
type Theme = 'light' | 'dark';
@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NavComponent implements OnInit {
  isLightTheme = false;
  constructor(
    private cdRef: ChangeDetectorRef,
    public authService: AuthService
  ) {
    const theme: Theme | null = localStorage.getItem('theme') as Theme;

    if (theme === 'dark') this.isLightTheme = false;
    else if (theme === 'light') this.isLightTheme = true;
    this.addThemeToBody(this.isLightTheme ? 'light' : 'dark');
  }

  ngOnInit(): void {}

  changeTheme() {
    this.isLightTheme = !this.isLightTheme;
    document.body.classList.toggle('dark');
    let theme: Theme = 'dark';
    if (this.isLightTheme) theme = 'light';
    localStorage.setItem('theme', theme);
    this.addThemeToBody(theme);
  }
  private addThemeToBody(theme: Theme) {
    if (theme === 'dark') {
      document.body.classList.add('dark');
    } else {
      document.body.classList.remove('dark');
    }
  }
}
