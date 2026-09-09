# GitHub Pages 部署说明

本书以 HonKit 构建静态站点，可用 GitHub Pages 发布。

## 本地构建与预览（默认）

```bash
npm install
npm run build
npm run serve
# http://localhost:4000/
```

构建产物目录：`_book/`。

本地预览与公开维护并用的方式：`npm run serve` 用于预览最新工作树，公开站点由 GitHub Pages 自动发布 `main` 分支。

## GitHub Pages（GitHub Actions）

仓库提供工作流：`.github/workflows/deploy-pages.yml`。

行为：

1. 在 `main` 分支推送时构建 HonKit
2. 上传 `_book` 作为 Pages 产物
3. 部署到 GitHub Pages

仓库设置：

1. `Settings` → `Pages`
2. Source 选择 `GitHub Actions`

发布后预期地址：

- `https://<user>.github.io/code-visualization-book/`

## 发布后检查

1. `npm run build` 成功
2. `SUMMARY.md` 与正文链接全部有效
3. 关键章 Mermaid / SVG 可渲染
4. 抽检样章：
   - `/part2/source-to-ast.html`
   - `/part4/change-impact-verification.html`
   - `/part5/agent-context-engineering.html`
5. 若改动影响数据契约（行号/实体 ID），对照 `.github/workflows/deploy-pages.yml` 部署后抽查对应页面内容新鲜度

## 历史/其他地址

- 旧 GitBook 站点（code-visualization.shawnxie.top）保存历史版本，可能落后于本仓库 RC 文稿；当前以 GitHub Pages（或等价地址）为准。
- 阅读最新内容：公开使用 GitHub Pages，工作树未推送内容用 `npm run serve`。

## 手动部署（可选）

若不用 Actions，也可将 `_book` 发布到 `gh-pages` 分支，或同步到任意静态托管。
