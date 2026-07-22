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

本地预览是当前确认 RC 内容的权威方式。远端发布需作者明确授权后再执行。

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

## 发布前检查

1. `npm run build` 成功
2. `SUMMARY.md` 与正文链接全部有效
3. 关键章 Mermaid / SVG 可渲染
4. 抽检样章：
   - `/part2/source-to-ast.html`
   - `/part4/change-impact-verification.html`
   - `/part5/agent-context-engineering.html`
5. 对照 [`definition-of-done.md`](definition-of-done.md) 第 12 节，确认 E006（公开同步）可关闭

## 历史/其他地址

- 旧 GitBook / 历史域名若仍可访问，可能不是本仓库最新 RC 文稿，发布后应以本仓库 Pages（或你指定的等价地址）为准。
- 在公开同步完成前，请以本地 `npm run serve` 阅读最新内容。

## 手动部署（可选）

若不用 Actions，也可将 `_book` 发布到 `gh-pages` 分支，或同步到任意静态托管。
