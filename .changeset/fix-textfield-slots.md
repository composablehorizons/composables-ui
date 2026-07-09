---
"@composables/ui": patch
---

Fix a bug where focusing on `TextField`'s leading or trailing content and then typing (via hardware keyboard) would cause the field to consume the keypresses. 
