# ihwaseok.github.io — 정적 개발 문서 사이트

## 목적

프로그래밍 언어, 프레임워크, 개발 환경 등 개인 학습 내용을 웹 문서로 공개하는 사이트입니다. Jekyll 문서 테마를 바탕으로 만들었으며, Joplin에서 내보낸 노트와 테마의 예제 문서·블로그 글이 함께 들어 있습니다. GitHub Pages 게시를 위한 의존성과 저장소 설정이 포함되어 있습니다.

## 기술 스택

- Ruby, Bundler, Jekyll 및 `github-pages` 패키지
- Markdown, Kramdown, Liquid 기반 레이아웃·공통 템플릿
- HTML/CSS, Bootstrap, jQuery, Rouge 코드 구문 강조
- Docker 및 Docker Compose를 이용한 로컬 미리보기 구성
- Java로 작성한 Joplin 문서 변환 보조 유틸리티

## 동작 방식

1. `pages/mydoc/Joplin`의 노트와 `_posts`의 글을 콘텐츠로 사용합니다.
2. `_config.yml`과 문서의 메타데이터를 통해 레이아웃, 사이드바, 주소 등을 결정합니다.
3. Jekyll이 Markdown을 HTML로 변환하고 `_layouts`, `_includes`, `_data`의 템플릿·메뉴 정보를 결합해 정적 페이지를 생성합니다.
4. 브라우저에서 사이드바, 태그, 검색 기능으로 문서를 탐색합니다. 별도의 애플리케이션 API나 데이터베이스 없이 정적 파일 중심으로 동작합니다.

`변환유틸/TestFile.java`는 폴더를 재귀 탐색해 파일·폴더명을 바꾸고, 노트에 Jekyll용 메타데이터와 리소스 경로를 반영하며 사이드바 YAML을 생성합니다. Joplin의 폴더 구조를 웹 문서 구조로 옮기는 보조 도구입니다.

**주요 파일:** [사이트 설정](./_config.yml), [의존성](./Gemfile), [변환 유틸리티](./변환유틸/TestFile.java), [Docker Compose](./docker-compose.yml)
