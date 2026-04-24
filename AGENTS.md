# Project Rules

## Active Design Skill

- Use `frontend-design` as the default design guidance for frontend UI work.
- Prioritize clean visual hierarchy, consistent spacing, readable typography, lightweight shadows, card-based layout, and restrained color usage.
- For recruitment-platform pages, keep UI decisions aligned with the product context: job discovery, recruiter workflows, candidate workflows, resumes, applications, interviews, and messaging.
- Do not modify the completed public homepage or Hero section unless the user explicitly asks for changes to that area.

## Frontend UI Priorities

- Prefer `shadcn/ui` components for any new React-based UI work in this project.
- Prefer `reactbits` for animation and motion effects in any new React-based UI work.
- Before building custom React UI primitives, check whether the requirement can be satisfied by `shadcn/ui`.
- Before adding a new animation library to a React surface, check whether `reactbits` already covers the interaction.

## Current Repo Constraint

- The current frontend in this repository is `Vue 3 + TypeScript + Vite`, not React.
- Do not force `shadcn/ui` or `reactbits` into the existing Vue codebase.
- For the current Vue frontend, preserve Vue-compatible solutions unless the user explicitly asks for a React migration or a new React subproject.

## Decision Rule

- If the task targets the existing `frontend` app, use Vue-compatible components and animation solutions.
- If the task creates a new React frontend, landing page, design system, or isolated React module, default to `shadcn/ui` and `reactbits`.

## Product Requirement Rule

- When the user describes recruitment-product requirements, treat them as implementation requirements for this repository unless they explicitly ask for advice only.
- Prefer landing requirements into routes, pages, visible UI states, and demonstrable user flows instead of returning only product suggestions.
