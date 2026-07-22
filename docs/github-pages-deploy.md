# GitHub Pages 部署说明

本书以 HonKit 构建静态站点，可用 GitHub Pages 发布。

## 本地构建

```bash
npm install
npm run build
```

构建产物目录：`_book/`。

本地预览：

```bash
npm run serve
# http://localhost:4000/
```

## GitHub Pages（GitHub Actions）

仓库已提供工作流：`.github/workflows/deploy-pages.yml`。

行为：

1. 在 `main` 分支推送时构建 HonKit
2. 上传 `_book` 作为 Pages 产物
3. 部署到 GitHub Pages

仓库设置：

1. `Settings` -> `Pages`
2. Source 选择 `GitHub Actions`

## 手动部署（可选）

若不用 Actions，也可将 `_book` 内容发布到 `gh-pages` 分支，或同步到任意静态托管。

当前历史阅读地址（若仍指向旧托管，可在发布后替换）：

- https://xiexiao064.gitbook.io/code-visualization

## 发布前检查

1. `npm run build` 成功
2. `SUMMARY.md` 链接全部有效
3. 关键章 Mermaid 可渲染
4. 抽检样章：
   - `/part2/source-to-ast.html`
   - `/part4/change-impact-verification.html`
   - `/part5/agent-context-engineering.html`
5. 对照 `docs/definition-of-done.md` 的 v1 清单

## 当前公开站点说明

现网 `https://code-visualization.shawnxie.top/`（由 gitbook.io 跳转）目前仍可能是**旧版内容**，不等于本仓库 RC 文稿。

RC 发布完成标准是：

1. 本仓库最新内容已发布到公开地址
2. 公开地址可打开前言与样章（如 `source-to-ast`）
3. 内容与本地 `npm run build` 产物一致（主线/案例/术语）

在完成同步前，请以本仓库与本地预览为准。
