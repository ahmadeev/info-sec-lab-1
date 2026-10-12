#import "@local/ucheb:0.2.0": bibliography-section, structural-section, ucheb-report

#let report-title = [Разработка защищенного REST API с интеграцией в CI/CD]
#let report-author = "Ахмадеев Данис Русланович"
#let sections = (
  assignment: none,
  annotation: none,
  contents: false,
  abbreviations: none,
  introduction: none,
  chapters: (
    include "sections/01-main.typ",
  ),
  conclusion: none,
  bibliography: none,
)

#set document(
  title: report-title,
  author: report-author,
)

#show: ucheb-report.with(
  document-kind: [Лабораторная работа № 1],
  discipline: [Информационная безопасность],
  title: report-title,
  author: report-author,
  group: [P3413],
  city: [Санкт-Петербург],
  year: datetime.today().year(),
  front-matter: (
    assignment: sections.assignment,
    annotation: sections.annotation,
    contents: sections.contents,
    abbreviations: sections.abbreviations,
  ),
)

#if sections.introduction != none {
  structural-section([ВВЕДЕНИЕ], sections.introduction)
}

#if sections.chapters != none {
  for chapter in sections.chapters {
    if chapter != none {
      chapter
    }
  }
}

#if sections.conclusion != none {
  structural-section([ЗАКЛЮЧЕНИЕ], sections.conclusion)
}

#if sections.bibliography != none {
  bibliography-section(sections.bibliography)
}
